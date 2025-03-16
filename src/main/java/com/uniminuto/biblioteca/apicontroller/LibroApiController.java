package com.uniminuto.biblioteca.apicontroller;


import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.services.LibroService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.List;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author lmora
 */
@RestController
@RequestMapping("/libro") // Define la ruta base del controlador
public class LibroApiController {

    @Autowired
    private LibroService libroService;

    /** 
     * Endpoint para listar todos los libros.
     * @return Lista de libros.
     */
    @GetMapping
    public ResponseEntity<List<Libro>> listarLibros() {
        return ResponseEntity.ok(libroService.listarLibros());
    }

    /** 
     * Endpoint para obtener un libro por su ID.
     * @param libroId ID del libro.
     * @return Libro encontrado.
     */
    @GetMapping("/{libroId}")
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Integer libroId) {
        try {
            return ResponseEntity.ok(libroService.obtenerLibroId(libroId));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado con ID: " + libroId);
        }
    }

    /** 
     * Endpoint para listar libros de un autor por su ID.
     * @param autorId ID del autor.
     * @return Lista de libros del autor.
     */
    @GetMapping("/autor/{autorId}")
    public ResponseEntity<List<Libro>> listarLibrosPorAutor(@PathVariable Integer autorId) {
    try {
        List<Libro> libros = libroService.listarLibrosPorAutor(autorId);
        return ResponseEntity.ok(libros);// Si la lista está vacía, se retorna vacía sin error
    } catch (EntityNotFoundException  e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el autor con ID: " + autorId);
        }
    }
    /**
     * Endpoint para obtener un libro por su título exacto (case-sensitive).
     * @param titulo Título exacto del libro.
     * @return Libro encontrado.
     */
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Libro> obtenerLibroPorTitulo(@PathVariable String titulo) {
        try {
            // Busca el libro por título
            Libro libro = libroService.obtenerLibroPorTitulo(titulo);
            return ResponseEntity.ok(libro);
        } catch (EntityNotFoundException e) {
            // Devuelve un error 404 si el libro no existe
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    /**
     * Endpoint para listar libros por un rango de fechas de publicación.
     * 
      @param anioInicio Año de inicio del rango.
     * @param anioFin Año de finalización del rango.
     * @return Lista de libros cuyo año de publicación está dentro del rango.
     */
    @GetMapping("/anio")
    public ResponseEntity<List<Libro>> listarLibrosPorAnioPublicacion(
            @RequestParam Integer anioInicio,
            @RequestParam Integer anioFin) {
        try {
            List<Libro> libros = libroService.listarLibrosPorAnioPublicacion(anioInicio, anioFin);
            return ResponseEntity.ok(libros);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
