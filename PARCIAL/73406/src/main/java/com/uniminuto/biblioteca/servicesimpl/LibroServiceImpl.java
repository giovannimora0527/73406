package com.uniminuto.biblioteca.servicesimpl;


import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class LibroServiceImpl implements LibroService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LibroServiceImpl.class);

    // Inyectar el repositorio de libros
    @Autowired
    private LibroRepository libroRepository;

    // Inyectar el repositorio de autores
    @Autowired
    private AutorRepository autorRepository;


    /**
     * @return Lista de libros
     * @throws BadRequestException
     */
    @Override
    public List<Libro> listarLibros() throws BadRequestException {return this.libroRepository.findAll();
    }

    /**
     * @param libroId
     * @return Obtiene un libro por su id
     * @throws BadRequestException
     */
    @Override
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = "
                    + libroId);
        }return optLibro.get();
    }

    /**
     * @param autorId
     * @return Obtiene una lista de libros asociados a ese autor
     * @throws BadRequestException
     */
    @Override
    public List<Object> listarLibrosPorIdAutor(Integer autorId) throws BadRequestException {
        // Verificar si id_autor existe en la tabla
        if (!autorRepository.existsById(autorId)) {
            throw new BadRequestException("No existe un autor con el ID = " + autorId);
        }

        // Buscar libros asociados al autor
        List<Object> listaLibrosByAutor = this.libroRepository.findAllBooksByAutorId(autorId);

        // Retornar la lista (puede estar vacía)
        return listaLibrosByAutor;
    }

    /**
     * @param tituloLibro
     * @return Obtiene un libro por su título
     * @throws BadRequestException
     */
    @Override
    public Libro buscarPorTitulo(String tituloLibro) throws BadRequestException {
        if (tituloLibro == null || tituloLibro.trim().isEmpty()) {
            throw new BadRequestException("El título del libro no puede estar vacío");
        }

        Libro libro = libroRepository.findByTitulo(tituloLibro);
        if (libro == null) {
            throw new BadRequestException("No se encontró ningún libro con el título exacto: " + tituloLibro);
        }

        return libro;
    }

    /**
     * @param fechaInicio
     * @param fechaFin
     * @return Obtiene una lista de libros por rango de fechas
     * @throws BadRequestException
     */
    @Override
    public List<Libro> buscarPorRangoFechas(Integer fechaInicio, Integer fechaFin) throws BadRequestException {

        // Validación de rango válido
        if (fechaInicio > fechaFin) {
            throw new BadRequestException("La fecha inicial (" + fechaInicio +
                    ") no puede ser mayor que la fecha final (" + fechaFin + ")");
        }

        // Validación de fechas negativas o fuera de rango razonable
        if (fechaInicio < 0 || fechaFin < 0 || fechaInicio > 9999 || fechaFin > 9999) {
            throw new BadRequestException("Las fechas deben estar entre 0 y 9999");
        }

        // Validación en log de datos ingresados
        log.info("Buscando libros entre {} y {}", fechaInicio, fechaFin);
        List<Libro> libros = libroRepository.findByRangoFechas(fechaInicio, fechaFin);
        log.info("Libros encontrados: {}", libros.size());

        // Debug: imprimir libros encontrados
        libros.forEach(libro ->
                log.debug("Libro: {}, Año: {}", libro.getTitulo(), libro.getAnioPublicacion())
        );

        // Validación de resultados
        if (libros.isEmpty()) {
            String mensaje = String.format("No se encontraron libros publicados entre los años %d y %d",
                    fechaInicio, fechaFin);
            log.warn(mensaje);
            throw new BadRequestException(mensaje);
        }

        return libros;
    }
}
