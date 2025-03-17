package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
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
@RequestMapping("/prestamo")
public interface PrestamoApi {

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
    ResponseEntity<List<Prestamo>> ListarPrestamos()
            throws BadRequestException;
    
     /**
     * Metodo para listar los prestamos registrados en bd.
     *
     * @param PrestamosId
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-prestamo-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Prestamo> obtenerPrestamosPorId (@RequestParam Integer PrestamosId)
            throws BadRequestException;
}
