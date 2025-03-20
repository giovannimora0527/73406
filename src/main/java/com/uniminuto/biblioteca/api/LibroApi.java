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
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/libro")
public interface LibroApi {

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibros()
            throws BadRequestException;
    
     /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param libroId Id del libro.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> obtenerLibroPorId(@RequestParam Integer libroId)
            throws BadRequestException;
    
        /**
     * Metodo para listar los libros por autor.
     *
     * @param idAutor Id del autor.
     * @return Lista de libros asociados al autor.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-por-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibrosPorAutor(@RequestParam Long idAutor)
            throws BadRequestException;
    
    
    /**
     * Metodo para listar los libros por titulo.
     *
     * @param titulo del titulo.
     * @return Lista de libros con el mismo titulo.
     * @throws BadRequestException excepcion.
    */
    @RequestMapping(value = "/libro-por-titulo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> obtenerLibroPorTitulo(@RequestParam String titulo)
            throws BadRequestException;
    
    /**
     * Metodo para listar los libros por Fechas.
     *
     * @param anioInicio
     * @param anioFin
     * @return Lista de libros con el mismo titulo.
     * @throws BadRequestException excepcion.
    */
    @RequestMapping(value = "/listar-por-Anio",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibrosPoranioPublicacion(
            @RequestParam int anioInicio,
            @RequestParam int anioFin
    ) throws BadRequestException;
}

