package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interfaz que define los métodos de la API para la gestión de libros.
 * Permite realizar operaciones como listar libros, obtener libros por su ID,
 * por autor, por título y por rango de fechas.
 * 
 * @author Santiago
 */
@CrossOrigin(origins = "*")
@RequestMapping("/libro")
public interface LibroApi {

    /**
     * Método para listar todos los libros registrados en la base de datos.
     * 
     * @return Lista de libros.
     * @throws BadRequestException Si ocurre algún error al procesar la solicitud.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibros()
            throws BadRequestException;

    /**
     * Método para obtener un libro por su ID.
     * 
     * @param libroId El ID del libro a obtener.
     * @return El libro encontrado.
     * @throws BadRequestException Si no se encuentra el libro con ese ID.
     */
    @RequestMapping(value = "/obtener-libro-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> obtenerLibroPorId(@RequestParam Integer libroId)
            throws BadRequestException;

    /**
     * Método para listar los libros de un autor específico.
     * 
     * @param idAutor El ID del autor cuyos libros se desean obtener.
     * @return Lista de libros del autor.
     * @throws BadRequestException Si no se encuentra el autor o si no tiene libros asociados.
     */
    @RequestMapping(value = "/listar-libros-por-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibrosPorAutor(@RequestParam Integer idAutor) throws BadRequestException;

    /**
     * Método para obtener un libro por su título exacto.
     * La búsqueda es sensible a mayúsculas y minúsculas.
     * 
     * @param titulo El título exacto del libro a obtener.
     * @return El libro encontrado.
     * @throws BadRequestException Si no se encuentra un libro con ese título.
     */
    @RequestMapping(value = "/obtener-libro-por-titulo",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> obtenerLibroPorTitulo(@RequestParam String titulo) throws BadRequestException;

    /**
     * Método para listar los libros publicados en un rango de fechas.
     * 
     * @param fechaInicio El año de inicio del rango de búsqueda.
     * @param fechaFin El año de fin del rango de búsqueda.
     * @return Lista de libros encontrados dentro del rango de fechas.
     * @throws BadRequestException Si no se encuentran libros dentro del rango de fechas o si las fechas son inválidas.
     */
    @RequestMapping(value = "/listar-libros-por-fecha",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibrosPorRangoFecha(
            @RequestParam("fechaInicio") Integer fechaInicio,
            @RequestParam("fechaFin") Integer fechaFin) throws BadRequestException;
}
