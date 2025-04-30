package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {

    @RequestMapping(
            value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<PrestamoRs>> listarPrestamos() throws BadRequestException;

    @RequestMapping(
            value = "/buscar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<PrestamoRs> obtenerPrestamoPorId(@RequestParam Integer idPrestamo) throws BadRequestException;

    @RequestMapping(
            value = "/registrar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<PrestamoRs> registrarPrestamo(@RequestBody PrestamoRq prestamo) throws BadRequestException;

    @RequestMapping(
            value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<PrestamoRs> actualizarPrestamo(@RequestBody PrestamoRq prestamo) throws BadRequestException;

    @RequestMapping(
            value = "/devolver",
            produces = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<PrestamoRs> registrarDevolucion(@RequestParam Integer idPrestamo) throws BadRequestException;

    @RequestMapping(
            value = "/usuario",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<PrestamoRs>> listarPrestamosPorUsuario(@RequestParam Integer idUsuario) throws BadRequestException;

    @RequestMapping(
            value = "/libro",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<PrestamoRs>> listarPrestamosPorLibro(@RequestParam Integer idLibro) throws BadRequestException;

    @RequestMapping(
            value = "/vencidos",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<PrestamoRs>> listarPrestamosVencidos() throws BadRequestException;
} 