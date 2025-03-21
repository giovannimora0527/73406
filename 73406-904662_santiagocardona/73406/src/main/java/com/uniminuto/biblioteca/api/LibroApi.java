package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Libro;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API para la gestión de libros en la biblioteca.
 * Proporciona métodos para listar, buscar y filtrar libros.
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/libro")
public interface LibroApi {

    /**
     * Método para listar todos los libros registrados en la base de datos.
     *
     * @return Lista de libros.
     * @throws BadRequestException si ocurre un error en la consulta.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Libro>> listarLibros() throws BadRequestException;

    /**
     * Método para obtener un libro por su ID.
     *
     * @param libroId ID del libro.
     * @return Libro correspondiente al ID proporcionado.
     * @throws BadRequestException si el libro no existe.
     */
    @GetMapping("/libroId")
    ResponseEntity<Libro> obtenerLibroPorId(@RequestParam Integer libroId) throws BadRequestException;

    /**
     * Método para listar libros asociados a un autor específico.
     *
     * @param autorId ID del autor.
     * @return Lista de libros escritos por el autor indicado.
     * @throws BadRequestException si no hay libros asociados al autor.
     */
    @GetMapping("/librosByAutorId")
    ResponseEntity<List<Libro>> listarLibrosPorAutorId(@RequestParam Integer autorId) throws BadRequestException;

    /**
     * Método para buscar un libro por su título exacto (case sensitive).
     *
     * @param titulo Título exacto del libro.
     * @return Libro encontrado con el título indicado.
     * @throws BadRequestException si el libro no existe o el título está vacío.
     */
    @GetMapping("/buscarPorTitulo")
    ResponseEntity<Libro> buscarPorTitulo(@RequestParam String titulo) throws BadRequestException;

    /**
     * Método para buscar libros publicados en un rango de años.
     *
     * @param fechaInicio Año inicial del rango.
     * @param fechaFin Año final del rango.
     * @return Lista de libros publicados dentro del rango especificado.
     * @throws BadRequestException si no hay libros en ese rango o las fechas son inválidas.
     */
    @GetMapping("/buscarPorFechas")
    ResponseEntity<List<Libro>> buscarPorRangoFechas(
            @RequestParam Integer fechaInicio,
            @RequestParam Integer fechaFin) throws BadRequestException;
}

