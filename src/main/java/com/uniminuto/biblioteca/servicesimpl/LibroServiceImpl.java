package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementación del servicio de libros.
 * Esta clase maneja la lógica de negocio relacionada con los libros en el sistema,
 * incluyendo la obtención de libros por autor, título y rangos de fechas de publicación.
 * 
 * @author santiago
 */
@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    /**
     * Obtiene una lista de todos los libros en el sistema.
     * 
     * @return Una lista de objetos Libro.
     * @throws BadRequestException Si ocurre un error durante la operación.
     */
    @Override
    public List<Libro> listarLibros() throws BadRequestException {
        return this.libroRepository.findAll();
    }

    /**
     * Obtiene un libro por su ID.
     * 
     * @param libroId El identificador único del libro.
     * @return El libro correspondiente al ID proporcionado.
     * @throws BadRequestException Si no se encuentra un libro con ese ID.
     */
    @Override
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = "
                    + libroId);
        }
        return optLibro.get();
    }

    /**
     * Obtiene una lista de libros de un autor específico, dado su ID.
     * 
     * @param idAutor El identificador del autor.
     * @return Lista de libros escritos por el autor.
     * @throws BadRequestException Si el autor no existe o no tiene libros registrados.
     */
    @Override
    public List<Libro> listarLibrosPorAutor(Integer idAutor) throws BadRequestException {
        // Verificar si el autor existe
        if (!autorRepository.existsById(idAutor)) {
            throw new BadRequestException("El autor con ID " + idAutor + " no está registrado.");
        }

        // Obtener los libros del autor
        List<Libro> libros = libroRepository.findByAutor_AutorId(idAutor);  // Asegúrate de usar el nuevo nombre

        if (libros.isEmpty()) {
            throw new BadRequestException("No se encontraron libros para el autor con ID " + idAutor);
        }

        return libros;
    }

    /**
     * Obtiene un libro por su título exacto, respetando mayúsculas y minúsculas.
     * 
     * @param titulo El título del libro.
     * @return El libro correspondiente al título proporcionado.
     * @throws BadRequestException Si no se encuentra un libro con ese título.
     */
    @Override
    public Optional<Libro> obtenerLibroPorTitulo(String titulo) throws BadRequestException {
        // Busca el libro por el título exacto
        Optional<Libro> libro = libroRepository.findByTitulo(titulo);

        if (!libro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el título: " + titulo);
        }

        return libro;
    }

    /**
     * Obtiene una lista de libros que fueron publicados dentro de un rango de años.
     * 
     * @param fechaInicio El año de inicio del rango de búsqueda.
     * @param fechaFin El año de fin del rango de búsqueda.
     * @return Lista de libros dentro del rango de fechas especificado.
     * @throws ResponseStatusException Si las fechas no son válidas o si no se encuentran libros.
     */
    @Override
    public List<Libro> listarLibrosPorRangoFecha(Integer fechaInicio, Integer fechaFin) {
        // Validar las fechas
        if (fechaInicio == null || fechaFin == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las fechas de inicio y fin no pueden ser nulas.");
        }
        if (fechaInicio > fechaFin) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        // Obtener los libros dentro del rango de años
        List<Libro> libros = libroRepository.findByAnioPublicacionBetween(fechaInicio, fechaFin);

        if (libros.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontraron libros en el rango de fechas especificado.");
        }

        return libros;
    }
}
