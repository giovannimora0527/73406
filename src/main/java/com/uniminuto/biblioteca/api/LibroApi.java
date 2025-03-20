package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
     * Método para obtener un libro por su nombre.
     * @param titulo
     * @return Libro encontrado.
     * @throws BadRequestException excepción.
     */
    @GetMapping("/obtener-libro-nombre")
    ResponseEntity<Libro> obtenerLibroPorNombre(@RequestParam String titulo) throws BadRequestException;
    /**
    * Método para obtener libros dentro de un rango de fechas de publicación.
    * @param fechaInicio Fecha inicial.
    * @param fechaFin Fecha final.
    * @return Lista de libros en el rango de fechas.
    */
   @GetMapping("/listar-por-fecha")
   ResponseEntity<List<Libro>> listarLibrosPorFecha(
       @RequestParam String fechaInicio, 
       @RequestParam String fechaFin
   );
   /**
    * Método para obtener los libros de un autor específico.
    * @param autorId ID del autor.
    * @return Lista de libros escritos por el autor.
    * @throws BadRequestException excepción.
    */
   @GetMapping("/listar-por-autor")
   ResponseEntity<List<Libro>> obtenerLibrosPorAutor(@RequestParam Integer autorId) 
       throws BadRequestException;
}
