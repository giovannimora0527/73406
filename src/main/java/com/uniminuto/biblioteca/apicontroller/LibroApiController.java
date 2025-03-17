package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.LibroApi;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Controlador que implementa los métodos definidos en la interfaz "LibroApi".
 */
@RestController
public class LibroApiController implements LibroApi {
    
    @Autowired
    private LibroService libroService;
    
    /**
     * Lista todos los libros.
     * 
     * @return La lista de libros.
     */
    @Override
    public ResponseEntity<List<Libro>> listarLibros() {
        return ResponseEntity.ok(libroService.listarLibros());
    }

    /**
     * Obtiene un libro por su su ID.
     * 
     * @param libroId El ID del libro.
     * @return El libro correspondiente al ID proporcionado.
     */
    @Override
    public ResponseEntity<Libro> obtenerLibroPorId(@PathVariable Integer libroId) {
        return ResponseEntity.ok(libroService.obtenerLibroId(libroId));
    }
    
    /**
     * Obtiene una lista de libros respecto al autor.
     * 
     * @param autorId El ID del autor cuyos libros se desean listar.
     * @return La lista de libros escritos por el autor especificado.
     */
    @Override
    public ResponseEntity<List<Libro>> obtenerLibrosPorAutor(@PathVariable Integer autorId) {
        return ResponseEntity.ok(libroService.obtenerLibrosPorAutor(autorId));
    }
    
    /**
     * Busca un libro por su título.
     * 
     * @param titulo El título del libro a buscar.
     * @return El libro correspondiente al título proporcionado.
     */
    @Override
    public ResponseEntity<Libro> obtenerLibroPorNombre(@RequestParam String titulo) {
        return ResponseEntity.ok(libroService.obtenerLibroPorNombre(titulo));
    }
    
    /**
     * Obtiene una lista de libros publicados en un rango de años específico.
     * 
     * @param anioInicio Año inicial del rango de búsqueda.
     * @param anioFin Año final del rango de búsqueda.
     * @return Una lista de libros publicados dentro del rango especificado.
     * @throws ResponseStatusException Si el año inicial es mayor que el año final.
     */
    @Override
    public ResponseEntity<List<Libro>> obtenerLibrosPorRangoFechas(
        @RequestParam int anioInicio, 
        @RequestParam int anioFin) {

        if (anioInicio > anioFin) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El año de inicio no puede ser mayor que el año final.");
        }

        return ResponseEntity.ok(libroService.obtenerLibrosPorRangoAnios(anioInicio, anioFin));
    }
}
