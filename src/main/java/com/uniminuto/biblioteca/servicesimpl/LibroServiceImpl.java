package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Categoria;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.CategoriaRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.AutorService;
import com.uniminuto.biblioteca.services.LibroService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lmora
 */
@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AutorService autorService;

    @Override
    public List<Libro> listarLibros() throws BadRequestException {
        return this.libroRepository.findAll();
    }

    @Override
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = "
                    + libroId);
        }
        return optLibro.get();
    }

    @Override
    public List<Libro> obtenerLibrosPorAutor(Integer autorId) throws BadRequestException {
        if (autorId == null) {
            throw new BadRequestException("El id del autor no puede ser vacio.");
        }
        Autor autor = this.autorService.obtenerAutorPorId(autorId);
        if (autor == null) {
            throw new BadRequestException("El autor con el id ingresado no existe.");
        }
        List<Libro> librosAutor = this.libroRepository.findByAutor(autor);
        return !librosAutor.isEmpty() ? librosAutor : Collections.EMPTY_LIST;
    }

    @Override
    public Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException {
        if (nombreLibro.isBlank() || nombreLibro.isEmpty()) {
            throw new BadRequestException("El nombre del libro es obligatorio.");
        }
        Libro libro = this.libroRepository.findByTitulo(nombreLibro);
        if (libro == null) {
            throw new BadRequestException("No existe el libro con el nombre de "
                    + nombreLibro + ".");
        }
        return libro;
    }

    @Override
    public List<Libro> obtenerLibroXRangoPublicacion(Integer fechaInicio, Integer fechaFin) throws BadRequestException {
        if (fechaInicio == null) {
            throw new BadRequestException("La fecha de inicio es obligatoria.");
        }
        if (fechaFin == null) {
            throw new BadRequestException("La fecha final es obligatoria.");
        }

        if (fechaFin < fechaInicio) {
            throw new BadRequestException("La fecha final no puede ser menor que la inicial.");
        }

        return this.libroRepository.findByAnioPublicacionBetween(fechaInicio, fechaFin);
    }
    
    // CARGA DE LIBROS DESDE CSV
    @Override
    public Map<String, Object> cargarLibrosDesdeCSV(MultipartFile archivo) throws BadRequestException {
        // Validar el archivo
        if (archivo.isEmpty()) {
            throw new BadRequestException("El archivo está vacío");
        }

        if (!archivo.getOriginalFilename().endsWith(".csv")) {
            throw new BadRequestException("El archivo debe ser un CSV");
        }

        // Estadísticas de procesamiento
        int totalRegistros = 0;
        int registrosExitosos = 0;
        int registrosFallidos = 0;
        List<String> errores = new ArrayList<>();

        // Obtener el año actual para validación
        int anioActual = LocalDateTime.now().getYear();

        // Leer el archivo CSV usando BufferedReader
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

            // Leer la primera línea (cabecera)
            String cabecera = reader.readLine();
            if (cabecera == null) {
                throw new BadRequestException("El archivo CSV está vacío");
            }

            // Validar la cabecera
            Map<String, Integer> columnaIndices = validarYObtenerIndicesColumnas(cabecera);

            // Procesar cada línea del archivo
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Saltar líneas vacías
                if (linea.trim().isEmpty()) {
                    continue;
                }

                totalRegistros++;
                try {
                    // Procesar la línea y crear un libro
                    Libro libro = procesarLineaCSV(linea, columnaIndices, anioActual);
                    
                    // Verificar duplicados (por título únicamente)
                    if (existeLibroDuplicado(libro)) {
                        throw new BadRequestException("Ya existe un libro con el título '" + libro.getTitulo() + "'");
                    }
                    // Verificar duplicados (por título y autor)
                    if (existeLibroDuplicado(libro)) {
                        throw new BadRequestException("Ya existe un libro con el título '" + libro.getTitulo() + 
                                                     "' del autor " + libro.getAutor().getNombre());
                    }

                    libroRepository.save(libro);
                    registrosExitosos++;
                } catch (Exception e) {
                    registrosFallidos++;
                    errores.add("Error en línea " + totalRegistros + ": " + e.getMessage());
                    // Limitar la cantidad de errores en la respuesta
                    if (errores.size() >= 20) {
                        errores.add("... y más errores (truncado)");
                        break;
                    }
                }
            }
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Error al procesar el archivo: " + e.getMessage());
        }

        // Preparar respuesta
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("totalProcesados", totalRegistros);
        resultado.put("exitosos", registrosExitosos);
        resultado.put("fallidos", registrosFallidos);
        resultado.put("errores", errores);
        resultado.put("mensaje", "Proceso completado. Se importaron " + registrosExitosos + " de " + totalRegistros + " libros.");

        return resultado;
    }

    /**
     * Valida la cabecera del archivo CSV y devuelve un mapa con los índices de las columnas
     */
    private Map<String, Integer> validarYObtenerIndicesColumnas(String cabecera) throws BadRequestException {
        Map<String, Integer> indices = new HashMap<>();
        String[] columnas = cabecera.split(",");

        // Limpiar los nombres de las columnas
        for (int i = 0; i < columnas.length; i++) {
            columnas[i] = columnas[i].trim().replace("\"", "").toLowerCase();
        }

        // Buscar las columnas requeridas
        for (int i = 0; i < columnas.length; i++) {
            switch (columnas[i]) {
                case "titulo":
                    indices.put("titulo", i);
                    break;
                case "idautor":
                case "id_autor":
                case "autorid":
                    indices.put("idAutor", i);
                    break;
                case "aniopublicacion":
                case "anio_publicacion":
                case "año_publicacion":
                case "añopublicacion":
                    indices.put("anioPublicacion", i);
                    break;
                case "categoriaid":
                case "categoria_id":
                case "idcategoria":
                case "id_categoria":
                    indices.put("categoriaId", i);
                    break;
                case "existencias":
                    indices.put("existencias", i);
                    break;
            }
        }

        // Verificar que todas las columnas requeridas estén presentes
        String[] columnasRequeridas = {"titulo", "idAutor", "anioPublicacion", "categoriaId", "existencias"};
        List<String> columnasFaltantes = new ArrayList<>();

        for (String columna : columnasRequeridas) {
            if (!indices.containsKey(columna)) {
                columnasFaltantes.add(columna);
            }
        }

        if (!columnasFaltantes.isEmpty()) {
            throw new BadRequestException("Faltan las siguientes columnas en el archivo CSV: " + 
                                         String.join(", ", columnasFaltantes) + 
                                         ". Las columnas requeridas son: titulo, idAutor, anioPublicacion, categoriaId, existencias");
        }

        return indices;
    }

    /**
     * Verifica si ya existe un libro con el mismo título y autor
     * CORRECCIÓN: Usa el método específico del repositorio
     */
    private boolean existeLibroDuplicado(Libro libro) {
        try {
            // Usar el método específico del repositorio si está disponible
            return libroRepository.existsByTituloIgnoreCaseAndAutor(libro.getTitulo(), libro.getAutor());
        } catch (Exception e) {
            // Método de respaldo usando findAll
            try {
                List<Libro> todosLosLibros = libroRepository.findAll();
                return todosLosLibros.stream()
                        .anyMatch(l -> l.getTitulo().equalsIgnoreCase(libro.getTitulo()) && 
                                      l.getAutor().getAutorId().equals(libro.getAutor().getAutorId()));
            } catch (Exception ex) {
                // Si también falla el respaldo, asumir que no existe duplicado para evitar bloquear la importación
                return false;
            }
        }
    }

    /**
     * Procesa una línea del CSV y crea un objeto Libro
     * @param anioActual El año actual para validación de fechas futuras
     * CORRECCIÓN: Mejor manejo del parsing CSV con comillas y comas
     */
    private Libro procesarLineaCSV(String linea, Map<String, Integer> columnaIndices, int anioActual) throws BadRequestException {
        // Dividir la línea en columnas (manejo mejorado de CSV)
        String[] datos = parsearLineaCSV(linea);

        if (datos.length < columnaIndices.size()) {
            throw new BadRequestException("La línea no tiene suficientes columnas. Esperadas: " + columnaIndices.size() + ", encontradas: " + datos.length);
        }

        try {
            // Extraer datos usando los índices obtenidos
            String titulo = datos[columnaIndices.get("titulo")].trim().replace("\"", "");
            String idAutorStr = datos[columnaIndices.get("idAutor")].trim().replace("\"", "");
            String anioPublicacionStr = datos[columnaIndices.get("anioPublicacion")].trim().replace("\"", "");
            String categoriaIdStr = datos[columnaIndices.get("categoriaId")].trim().replace("\"", "");
            String existenciasStr = datos[columnaIndices.get("existencias")].trim().replace("\"", "");

            // Validar campos obligatorios
            if (titulo.isEmpty()) {
                throw new BadRequestException("El título no puede estar vacío");
            }

            if (idAutorStr.isEmpty()) {
                throw new BadRequestException("El ID del autor no puede estar vacío");
            }

            if (anioPublicacionStr.isEmpty()) {
                throw new BadRequestException("El año de publicación no puede estar vacío");
            }

            if (categoriaIdStr.isEmpty()) {
                throw new BadRequestException("El ID de categoría no puede estar vacío");
            }

            if (existenciasStr.isEmpty()) {
                throw new BadRequestException("Las existencias no pueden estar vacías");
            }

            // Validar que el ID del autor sea numérico
            Integer idAutor;
            try {
                idAutor = Integer.parseInt(idAutorStr);
            } catch (NumberFormatException e) {
                throw new BadRequestException("El ID del autor debe ser un número válido: '" + idAutorStr + "'");
            }

            // Validar que el año de publicación solo contenga dígitos
            if (!anioPublicacionStr.matches("\\d+")) {
                throw new BadRequestException("El año de publicación debe contener solo dígitos: '" + anioPublicacionStr + "'");
            }

            Integer anioPublicacion;
            try {
                anioPublicacion = Integer.parseInt(anioPublicacionStr);
            } catch (NumberFormatException e) {
                throw new BadRequestException("El año de publicación debe ser un número válido: '" + anioPublicacionStr + "'");
            }

            // Validar que el año no sea futuro
            if (anioPublicacion > anioActual) {
                throw new BadRequestException("El año de publicación '" + anioPublicacion + 
                                             "' no puede ser mayor al año actual (" + anioActual + ")");
            }

            // Validar año mínimo razonable (por ejemplo, año 1000)
            if (anioPublicacion < 1000) {
                throw new BadRequestException("El año de publicación '" + anioPublicacion + "' no es válido");
            }

            // Validar ID de categoría
            Integer categoriaId;
            try {
                categoriaId = Integer.parseInt(categoriaIdStr);
            } catch (NumberFormatException e) {
                throw new BadRequestException("El ID de categoría debe ser un número válido: '" + categoriaIdStr + "'");
            }

            // Validar existencias
            Integer existencias;
            try {
                existencias = Integer.parseInt(existenciasStr);
            } catch (NumberFormatException e) {
                throw new BadRequestException("Las existencias deben ser un número válido: '" + existenciasStr + "'");
            }

            if (existencias < 0) {
                throw new BadRequestException("Las existencias no pueden ser negativas");
            }

            // Verificar que el autor exista
            Optional<Autor> optAutor = autorRepository.findById(idAutor);
            if (!optAutor.isPresent()) {
                throw new BadRequestException("El autor con ID " + idAutor + " no existe en el sistema");
            }

            // Verificar que la categoría exista
            Optional<Categoria> optCategoria = categoriaRepository.findById(categoriaId);
            if (!optCategoria.isPresent()) {
                throw new BadRequestException("La categoría con ID " + categoriaId + " no existe");
            }

            // Crear el libro
            Libro libro = new Libro();
            libro.setTitulo(titulo);
            libro.setAutor(optAutor.get());
            libro.setAnioPublicacion(anioPublicacion);
            libro.setCategoria(optCategoria.get());
            libro.setExistencias(existencias);

            return libro;
        } catch (NumberFormatException e) {
            throw new BadRequestException("Error al convertir un valor numérico: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            throw new BadRequestException("Error al acceder a una columna: la línea no tiene suficientes campos");
        }
    }

    /**
     * MÉTODO AGREGADO: Parsea una línea CSV manejando correctamente las comillas y comas
     */
    private String[] parsearLineaCSV(String linea) {
        List<String> campos = new ArrayList<>();
        boolean dentroDeComillas = false;
        StringBuilder campoActual = new StringBuilder();

        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);

            if (c == '"') {
                // Alternar estado de comillas
                dentroDeComillas = !dentroDeComillas;
            } else if (c == ',' && !dentroDeComillas) {
                // Si encontramos una coma fuera de comillas, terminar el campo actual
                campos.add(campoActual.toString());
                campoActual = new StringBuilder();
            } else {
                // Agregar el carácter al campo actual
                campoActual.append(c);
            }
        }

        // Agregar el último campo
        campos.add(campoActual.toString());

        return campos.toArray(new String[0]);
    }
}