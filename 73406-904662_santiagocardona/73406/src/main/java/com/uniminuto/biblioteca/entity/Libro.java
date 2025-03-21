package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.LibroApi;
import java.util.List;


import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.services.LibroService;
import org.apache.coyote.BadRequestException;
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
     * Metodo para listar los libros registrado por AutorId.
     *
     * @param autorId
     * @return Lista de libros por Id.
     * @throws BadRequestException excepcion.
     */
    @Override
    public ResponseEntity<List<Object>>listarLibrosPorAutorId(Integer autorId) throws BadRequestException {
        return ResponseEntity.ok(this.libroService.listarLibrosPorIdAutor(autorId));
    }

    @Override
    public ResponseEntity<Libro> buscarPorTitulo(String titulo) throws BadRequestException {
        return ResponseEntity.ok(libroService.buscarPorTitulo(titulo));
    }

    /**
     * @param fechaInicio Año inicial del rango
     * @param fechaFin    Año final del rango
     * @return
     * @throws BadRequestException
     */
    @Override
    public ResponseEntity<List<Libro>> buscarPorRangoFechas(Integer fechaInicio, Integer fechaFin) throws BadRequestException {
        return ResponseEntity.ok(libroService.buscarPorRangoFechas(fechaInicio, fechaFin));
    }
}
