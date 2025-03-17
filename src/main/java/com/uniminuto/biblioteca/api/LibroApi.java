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
     * Metodo para listar los libros por autores.
     *
     * @param autorId Id del libro.
     * @return Lista de libros por autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-por-autor",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> obtenerLibrosPorAutor(@RequestParam Integer autorId) throws BadRequestException;

    
     /**
     * Metodo para mostrar libro por nombre.
     *
     * @param titulo titulo del libro.
     * @return Muestra el libro de acuerdo al nombre.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/buscar-por-nombre",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> obtenerLibroPorNombre(@RequestParam String titulo) throws BadRequestException;

    
     /**
     * Metodo para mostrar lsita de libros por fecha de publicación.
     *
     * @param fechaInicio fecha inicial que se recibe como parámetro.
     * @param fechaFin fecha final que se pasa como parámetro para el rango
     * @return Muestra el libro de acuerdo al nombre.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-por-fecha",
        produces = {"application/json"},
        method = RequestMethod.GET) 
    ResponseEntity<List<Libro>> obtenerLibrosPorRangoFechas(
        @RequestParam int anioInicio, 
        @RequestParam int anioFin);
}

