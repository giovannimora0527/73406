package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz de servicio para la entidad Libro.
 * Esta interfaz define los métodos que deben ser implementados por cualquier clase
 * que gestione las operaciones relacionadas con los libros en el sistema,
 * como la obtención de libros por ID, por autor, por título, o por un rango de fechas.
 * 
 * @author Santiago
 */
public interface LibroService {

    /**
     * Obtiene una lista de todos los libros registrados en el sistema.
     * 
     * @return Una lista de objetos Libro.
     * @throws BadRequestException Si ocurre un error durante la operación.
     */
    List<Libro> listarLibros() throws BadRequestException;

    /**
     * Obtiene un libro a partir de su identificador único (ID).
     * 
     * @param libroId El identificador único del libro.
     * @return El libro correspondiente al ID proporcionado.
     * @throws BadRequestException Si no se encuentra el libro con el ID proporcionado.
     */
    Libro obtenerLibroId(Integer libroId) throws BadRequestException;

    /**
     * Obtiene una lista de libros pertenecientes a un autor específico.
     * 
     * @param idAutor El identificador único del autor.
     * @return Una lista de libros escritos por el autor con el ID proporcionado.
     * @throws BadRequestException Si el autor no tiene libros registrados o si el ID es inválido.
     */
    List<Libro> listarLibrosPorAutor(Integer idAutor) throws BadRequestException;

    /**
     * Obtiene un libro por su título exacto, respetando mayúsculas y minúsculas.
     * 
     * @param titulo El título exacto del libro.
     * @return Un objeto Optional que contiene el libro si se encuentra, o un Optional vacío si no se encuentra.
     * @throws BadRequestException Si no se encuentra el libro con ese título.
     */
    Optional<Libro> obtenerLibroPorTitulo(String titulo) throws BadRequestException;
    
    /**
     * Obtiene una lista de libros que fueron publicados dentro de un rango de años.
     * 
     * @param anioInicio El año de inicio del rango de búsqueda.
     * @param anioFin El año de fin del rango de búsqueda.
     * @return Una lista de libros publicados dentro del rango de años especificado.
     */
    List<Libro> listarLibrosPorRangoFecha(Integer anioInicio, Integer anioFin);
}
