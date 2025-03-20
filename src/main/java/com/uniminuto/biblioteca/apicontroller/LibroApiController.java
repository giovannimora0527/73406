package com.uniminuto.biblioteca.apicontroller;


import com.uniminuto.biblioteca.api.LibroApi;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;



/**
 *
 * @author lmora
 */
@RestController
public class LibroApiController implements LibroApi {
    
    @Autowired
    private LibroService libroService;
    
    @Override
    public ResponseEntity<List<Libro>> listarLibros()
            throws BadRequestException {
        return ResponseEntity.ok(this.libroService.listarLibros());
    }

    @Override
    public ResponseEntity<Libro> obtenerLibroPorId(Integer libroId) throws BadRequestException {
      return ResponseEntity.ok(this.libroService.obtenerLibroId(libroId));
    }

    
    /**
    * Controlador que gestiona las operaciones relacionadas con los libros.
    * 
     * Lista los libros asociados a un autor.
     *
     * @param idAutor Id del autor.
     * @return Lista de libros asociados al autor.
     * @throws BadRequestException Si el autor no existe.
     */
    @Override
    public ResponseEntity<List<Libro>> listarLibrosPorAutor(@RequestParam Long idAutor) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.listarLibrosPorAutor(idAutor));
    }
    
    /**
    * Obtiene un libro a partir de su título.
    *
    * @param titulo el título del libro a buscar.
    * @return ResponseEntity con el libro encontrado.
    * @throws BadRequestException si el libro no existe.
    */
    @Override
    public ResponseEntity<Libro> obtenerLibroPorTitulo(@RequestParam String titulo) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.obtenerLibroPorTitulo(titulo));
    }
    /**
    * Lista los libros publicados dentro de un rango de años.
    *
    * @param anioInicio el año de inicio del rango.
    * @param anioFin el año de fin del rango.
    * @return ResponseEntity con la lista de libros publicados dentro del rango especificado.
    * @throws BadRequestException si la fecha de inicio es mayor que la final
    *         o si no se encuentran libros en el rango.
    */
    @Override
    public ResponseEntity<List<Libro>> listarLibrosPoranioPublicacion(int anioInicio, int anioFin) throws BadRequestException {
        return ResponseEntity.ok(libroService.listarLibrosPoranioPublicacion(anioInicio, anioFin));
    }
}