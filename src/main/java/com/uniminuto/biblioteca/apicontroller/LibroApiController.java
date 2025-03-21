package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import com.uniminuto.biblioteca.api.LibroApi;
import org.apache.coyote.BadRequestException;







/**
 * Controlador para gestionar los libros.
 */
@RestController
@RequestMapping("/libro")
public class LibroApiController implements LibroApi {
    
    @Autowired
    private LibroService libroService;

    @Override
    public ResponseEntity<List<Libro>> listarLibros() throws BadRequestException {
        return ResponseEntity.ok(libroService.listarLibros());
    }

    @Override
    public ResponseEntity<Libro> obtenerLibroPorId(@RequestParam Integer libroId) {
        return libroService.obtenerLibroPorId(libroId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontró un libro con el ID: " + libroId));
    }

    @Override
    @GetMapping("/autor/{autorId}")
    public ResponseEntity<List<Libro>> obtenerLibrosPorAutor(@PathVariable Integer autorId) {
        List<Libro> libros = libroService.obtenerLibrosPorAutor(autorId);
        return ResponseEntity.ok(libros);
    }


    @Override
    public ResponseEntity<Libro> obtenerLibroPorNombre(@RequestParam String titulo) {
        return libroService.obtenerLibroPorNombre(titulo)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontró un libro con el título: " + titulo));
    }

    @Override
    public ResponseEntity<List<Libro>> listarLibrosPorFecha(@RequestParam Integer inicio, @RequestParam Integer fin) {
        if (inicio > fin) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El año de inicio no puede ser mayor que el año de fin.");
        }
        List<Libro> libros = libroService.listarLibrosPorRangoFechas(inicio, fin);
        if (libros.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontraron libros en el rango de fechas: " + inicio + " - " + fin);
        }
        return ResponseEntity.ok(libros);
    }
}