package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.LibroApi;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 * Controlador que implementa los métodos definidos en la interfaz LibroApi.
 * Proporciona los endpoints necesarios para gestionar libros,
 * como listar libros, obtener libros por ID, título o autor, 
 * y listar libros en un rango de fechas.
 * 
 * @author Santiago
 */
@RestController
public class LibroApiController implements LibroApi {

    @Autowired
    private LibroService libroService;

    /**
     * Método para listar todos los libros registrados en la base de datos.
     * 
     * @return Lista de libros.
     * @throws BadRequestException Si ocurre un error al procesar la solicitud.
     */
    @Override
    public ResponseEntity<List<Libro>> listarLibros()
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService.listarLibros());
    }

    /**
     * Método para obtener un libro por su ID.
     * 
     * @param libroId El ID del libro a obtener.
     * @return El libro encontrado.
     * @throws BadRequestException Si no se encuentra el libro con ese ID.
     */
    @Override
    public ResponseEntity<Libro> obtenerLibroPorId(Integer libroId) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroId(libroId));
    }

    /**
     * Método para listar los libros de un autor específico.
     * 
     * @param idAutor El ID del autor cuyos libros se desean obtener.
     * @return Lista de libros del autor.
     * @throws BadRequestException Si no se encuentra el autor o si no tiene libros asociados.
     */
    @Override
    public ResponseEntity<List<Libro>> listarLibrosPorAutor(Integer idAutor) throws BadRequestException {
        List<Libro> libros = libroService.listarLibrosPorAutor(idAutor);
        return ResponseEntity.ok(libros);
    }

    /**
     * Método para obtener un libro por su título exacto.
     * 
     * @param titulo El título exacto del libro a obtener.
     * @return El libro encontrado.
     * @throws BadRequestException Si no se encuentra un libro con ese título.
     */
    @Override
    public ResponseEntity<Libro> obtenerLibroPorTitulo(String titulo) throws BadRequestException {
        Optional<Libro> libro = libroService.obtenerLibroPorTitulo(titulo);
        if (libro.isPresent()) {
            return ResponseEntity.ok(libro.get());
        } else {
            throw new BadRequestException("No se encuentra el libro con el título: " + titulo);
        }
    }

    /**
     * Método para listar los libros publicados en un rango de fechas.
     * 
     * @param fechaInicio El año de inicio del rango de búsqueda.
     * @param fechaFin El año de fin del rango de búsqueda.
     * @return Lista de libros encontrados dentro del rango de fechas.
     */
    @Override
    public ResponseEntity<List<Libro>> listarLibrosPorRangoFecha(Integer fechaInicio, Integer fechaFin) {
        List<Libro> libros = libroService.listarLibrosPorRangoFecha(fechaInicio, fechaFin);
        return ResponseEntity.ok(libros);
    }
}
