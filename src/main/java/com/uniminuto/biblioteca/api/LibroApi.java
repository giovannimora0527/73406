package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/libro")
public interface LibroApi {

    /**
     * Listar todos los libros.
     * @return Lista de libros.
     * @throws BadRequestException Excepción en caso de error.
     */
    @GetMapping
    ResponseEntity<List<Libro>> listarLibros() throws BadRequestException;

    /**
     * Obtener un libro por ID.
     * @param id ID del libro a buscar.
     * @return Libro encontrado o excepción si no existe.
     * @throws BadRequestException Excepción en caso de error.
     */
    @GetMapping("/{id}")
    ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Integer id) throws BadRequestException;

    /**
     * Obtener libros por el ID de un autor.
     * @param autorId ID del autor.
     * @return Lista de libros del autor.
     * @throws BadRequestException Excepción en caso de error.
     */
    @GetMapping("/autor/{autorId}")
    ResponseEntity<List<Libro>> obtenerLibrosPorAutor(@PathVariable Integer autorId) throws BadRequestException;

    /**
     * Obtener un libro por título exacto.
     * @param titulo Título del libro.
     * @return Libro encontrado.
     * @throws BadRequestException Excepción en caso de error.
     */
    @GetMapping("/buscar")
    ResponseEntity<Libro> obtenerLibroPorNombre(@RequestParam String titulo) throws BadRequestException;

    /**
     * Obtener libros por rango de fechas.
     * @param inicio Año de inicio.
     * @param fin Año de fin.
     * @return Lista de libros en el rango de fechas.
     * @throws BadRequestException Excepción en caso de error.
     */
    @GetMapping("/buscar-por-fecha")
    ResponseEntity<List<Libro>> listarLibrosPorFecha(@RequestParam Integer inicio, @RequestParam Integer fin) throws BadRequestException;

}