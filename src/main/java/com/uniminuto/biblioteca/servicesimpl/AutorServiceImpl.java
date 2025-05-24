package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.NacionalidadRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lmora
 */
@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;
    
    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @Override
    public List<Autor> obtenerListadoAutores() {
        return this.autorRepository.findAllByOrderByFechaNacimientoDesc();
    }

    @Override
    public Autor obtenerAutorPorId(Integer autorId) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autorId);
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + autorId);
        }
        return optAutor.get();
    }
    
    @Override
    public List<Nacionalidad> obtenerNacionalidadesDesdeAutores() throws BadRequestException {
        return autorRepository.listarNacionalidadesDeAutores();
    }
    
    @Override
    public List<Autor> listarAutoresPorNacionalidad(Integer nacionalidadId) {
        return autorRepository.findByNacionalidad_NacionalidadId(nacionalidadId);
    }
    
    @Override
    public List<Autor> obtenerListadoAutoresPorNacionalidadId(String nacionalidadId) throws BadRequestException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AutorRs guardarAutorNuevo(AutorRq autor) throws BadRequestException {
        // VALIDACIÓN AGREGADA: Verificar que el nombre solo contenga letras y espacios
        if (!validarNombreAutor(autor.getNombre())) {
            throw new BadRequestException("El nombre del autor solo puede contener letras y espacios. "
                    + "No se permiten números ni caracteres especiales.");
        }
        
        Optional<Autor> optAutor = this.autorRepository.findByNombre(autor.getNombre());

        if (optAutor.isPresent()) {
            throw new BadRequestException("El autor ya existe con el nombre "
                    + autor.getNombre()
                    + ", verifique e intente de nuevo.");
        }

        Nacionalidad nacionalidad = nacionalidadRepository.findById(autor.getNacionalidadId())
                .orElseThrow(() -> new BadRequestException("Nacionalidad no encontrada"));

        Autor nuevoAutor = new Autor();
        nuevoAutor.setNombre(autor.getNombre().trim()); // Limpiar espacios adicionales
        nuevoAutor.setFechaNacimiento(autor.getFechaNacimiento());
        nuevoAutor.setNacionalidad(nacionalidad);

        this.autorRepository.save(nuevoAutor);

        AutorRs rta = new AutorRs();
        rta.setMessage("Se ha guardado el autor con éxito.");
        return rta;
    }

    @Override
    public AutorRs actualizarAutor(AutorRq autor) throws BadRequestException {
        // VALIDACIÓN AGREGADA: Verificar que el nombre solo contenga letras y espacios
        if (!validarNombreAutor(autor.getNombre())) {
            throw new BadRequestException("El nombre del autor solo puede contener letras y espacios. "
                    + "No se permiten números ni caracteres especiales.");
        }
        
        Optional<Autor> optAutor = this.autorRepository.findById(autor.getAutorId());

        if (!optAutor.isPresent()) {
            throw new BadRequestException("No existe el autor.");
        }

        Autor autorActual = optAutor.get();

        if (!cambioObjeto(autorActual, autor)) {
            AutorRs sinCambios = new AutorRs();
            sinCambios.setMessage("No se realizaron cambios en el autor.");
            return sinCambios;
        }

        if (!autor.getNombre().equals(autorActual.getNombre())
                && this.autorRepository.existsByNombre(autor.getNombre())) {
            throw new BadRequestException("El nombre del autor: " + autor.getNombre()
                    + ", ya existe en la base de datos. Verifique e intente de nuevo.");
        }

        Nacionalidad nacionalidad = nacionalidadRepository.findById(autor.getNacionalidadId())
                .orElseThrow(() -> new BadRequestException("Nacionalidad no encontrada"));

        autorActual.setNombre(autor.getNombre().trim()); // Limpiar espacios adicionales
        autorActual.setFechaNacimiento(autor.getFechaNacimiento());
        autorActual.setNacionalidad(nacionalidad);

        this.autorRepository.save(autorActual);

        AutorRs rta = new AutorRs();
        rta.setMessage("El autor se actualizó correctamente.");
        return rta;
    }

    private boolean cambioObjeto(Autor actual, AutorRq nuevo) {
        return !actual.getNombre().equals(nuevo.getNombre())
                || !actual.getFechaNacimiento().equals(nuevo.getFechaNacimiento())
                || !actual.getNacionalidad().getNacionalidadId().equals(nuevo.getNacionalidadId());
    }
    
    /**
     * MÉTODO AGREGADO: Valida que el nombre del autor solo contenga letras, espacios y algunos caracteres especiales permitidos
     * @param nombre El nombre a validar
     * @return true si el nombre es válido, false en caso contrario
     */
    private boolean validarNombreAutor(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        
        // Limpiar espacios extra
        nombre = nombre.trim();
        
        // Verificar longitud mínima y máxima
        if (nombre.length() < 2 || nombre.length() > 100) {
            return false;
        }
        
        // Regex que permite:
        // - Letras (incluyendo acentos y ñ)
        // - Espacios
        // - Puntos (para iniciales como "J.R.R. Tolkien")
        // - Guiones (para nombres compuestos como "García-Márquez")
        // - Apostrofes (para nombres como "O'Connor")
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s\\.\\-']+$";
        
        if (!nombre.matches(regex)) {
            return false;
        }
        
        // Verificaciones adicionales:
        // 1. No debe empezar o terminar con espacios, puntos o guiones
        if (nombre.startsWith(" ") || nombre.endsWith(" ") || 
            nombre.startsWith(".") || nombre.endsWith(".") ||
            nombre.startsWith("-") || nombre.endsWith("-")) {
            return false;
        }
        
        // 2. No debe tener espacios dobles consecutivos
        if (nombre.contains("  ")) {
            return false;
        }
        
        // 3. No debe tener solo espacios o caracteres especiales
        if (nombre.replaceAll("[\\s\\.\\-']", "").isEmpty()) {
            return false;
        }
        
        return true;
    }
   
    // CARGA DE AUTORES DESDE CSV
    @Override
    @Transactional
    public Map<String, Object> cargarAutoresDesdeCSV(MultipartFile archivo) throws BadRequestException {
        Map<String, Object> resultado = new HashMap<>();
        List<String> errores = new ArrayList<>();
        List<Autor> autoresParaGuardar = new ArrayList<>();
        int totalProcesados = 0;
        int exitos = 0;

        // Validaciones básicas
        if (archivo.isEmpty()) {
            throw new BadRequestException("El archivo está vacío");
        }
        if (!archivo.getOriginalFilename().endsWith(".csv")) {
            throw new BadRequestException("El archivo debe tener formato CSV");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
            // Leer primera línea para determinar si tiene encabezado
            String linea = reader.readLine();
            if (linea == null) {
                throw new BadRequestException("El archivo no contiene datos");
            }

            // Verificar si es encabezado
            boolean tieneEncabezado = linea.toLowerCase().contains("nombre") || 
                                     linea.toLowerCase().contains("nacionalidad");

            // Lista de líneas a procesar
            List<String> lineasProcesar = new ArrayList<>();

            // Si no es encabezado, añadir primera línea
            if (!tieneEncabezado) {
                lineasProcesar.add(linea);
            }

            // Añadir resto de líneas
            String lineaActual;
            while ((lineaActual = reader.readLine()) != null) {
                if (!lineaActual.trim().isEmpty()) {
                    lineasProcesar.add(lineaActual);
                }
            }

            totalProcesados = lineasProcesar.size();

            // Primera pasada: validar todas las líneas antes de guardar cualquier autor
            for (int i = 0; i < lineasProcesar.size(); i++) {
                String lineaProcesar = lineasProcesar.get(i);
                try {
                    Autor autorProcesado = validarYPrepararAutor(lineaProcesar, i + 1, errores);
                    if (autorProcesado != null) {
                        autoresParaGuardar.add(autorProcesado);
                        exitos++;
                    }
                } catch (Exception e) {
                    errores.add("Error en línea " + (i + 1) + ": " + e.getMessage());
                }
            }

            // Si hay algún error, no guardamos nada
            if (!errores.isEmpty()) {
                throw new BadRequestException("Se encontraron errores en el archivo CSV");
            }

            // Si no hay errores, guardamos todos los autores
            for (Autor autor : autoresParaGuardar) {
                autorRepository.save(autor);
            }

        } catch (IOException e) {
            throw new BadRequestException("Error al leer el archivo: " + e.getMessage());
        }

        // Preparar resultado
        resultado.put("totalProcesados", totalProcesados);
        resultado.put("exitosos", exitos);
        resultado.put("fallidos", totalProcesados - exitos);
        resultado.put("errores", errores);
        resultado.put("message", "Proceso completado. " + exitos + " de " + totalProcesados + " autores importados correctamente.");

        return resultado;
    }

    // Método para validar y preparar un autor sin guardarlo en la base de datos
    private Autor validarYPrepararAutor(String linea, int numeroLinea, List<String> errores) {
        try {
            // Split con regex que respeta comillas
            String[] campos = linea.split(",");

            // Verificar que estén todos los campos necesarios
            if (campos.length < 3) {
                errores.add("Línea " + numeroLinea + ": Formato incorrecto: se requieren exactamente 3 campos (nombre, nacionalidadId, fechaNacimiento)");
                return null;
            }

            // Extraer y limpiar campos
            String nombre = campos[0].trim().replace("\"", "");
            String nacionalidadIdStr = campos[1].trim().replace("\"", "");
            String fechaStr = campos[2].trim().replace("\"", "");

            // Validación: todos los campos deben estar presentes
            if (nombre.isEmpty() || nacionalidadIdStr.isEmpty() || fechaStr.isEmpty()) {
                errores.add("Línea " + numeroLinea + ": Todos los campos son obligatorios (nombre, nacionalidadId, fechaNacimiento)");
                return null;
            }

            // VALIDACIÓN AGREGADA: Verificar que el nombre solo contenga letras y espacios
            if (!validarNombreAutor(nombre)) {
                errores.add("Línea " + numeroLinea + ": El nombre '" + nombre + "' solo puede contener letras, espacios, puntos, guiones y apostrofes. No se permiten números ni otros caracteres especiales.");
                return null;
            }

            // Verificar duplicidad de nombre
            if (autorRepository.existsByNombre(nombre)) {
                errores.add("Línea " + numeroLinea + ": Ya existe un autor con el nombre: " + nombre);
                return null;
            }

            // Validar y convertir ID de nacionalidad
            Integer nacionalidadId;
            try {
                nacionalidadId = Integer.parseInt(nacionalidadIdStr);
            } catch (NumberFormatException e) {
                errores.add("Línea " + numeroLinea + ": ID de nacionalidad inválido: " + nacionalidadIdStr);
                return null;
            }

            // Verificar que la nacionalidad existe
            Optional<Nacionalidad> nacionalidadOpt = nacionalidadRepository.findById(nacionalidadId);
            if (!nacionalidadOpt.isPresent()) {
                errores.add("Línea " + numeroLinea + ": No existe nacionalidad con ID: " + nacionalidadId);
                return null;
            }

            // Validar y convertir fecha
            LocalDate fechaNacimiento;
            try {
                // Intentar varios formatos de fecha
                try {
                    fechaNacimiento = LocalDate.parse(fechaStr); // Formato estándar ISO
                } catch (DateTimeParseException e) {
                    try {
                        fechaNacimiento = LocalDate.parse(fechaStr, 
                                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    } catch (DateTimeParseException e2) {
                        fechaNacimiento = LocalDate.parse(fechaStr, 
                                DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    }
                }
            } catch (Exception e) {
                errores.add("Línea " + numeroLinea + ": Fecha inválida: " + fechaStr + " (use formato YYYY-MM-DD, DD/MM/YYYY o DD-MM-YYYY)");
                return null;
            }

            // Solo crear el autor si todos los campos son válidos
            Autor autor = new Autor();
            autor.setNombre(nombre.trim()); // Limpiar espacios adicionales
            autor.setFechaNacimiento(fechaNacimiento);
            autor.setNacionalidad(nacionalidadOpt.get());

            return autor;

        } catch (Exception e) {
            errores.add("Error procesando línea " + numeroLinea + ": " + e.getMessage());
            return null;
        }
    }
}