<<<<<<< HEAD
package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

>>>>>>> 708e27d9b1709601d41210363af62176929a3745

@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {

<<<<<<< HEAD
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
=======
        /**
         * Método para listar los préstamos registrados en la base de datos.
         *
         * @return Lista de préstamos.
         * @throws org.apache.coyote.BadRequestException
         */
        @RequestMapping(value = "/listar", method = RequestMethod.GET, produces = { "application/json" })
        ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException;

        /**
         * Guarda un nuevo préstamo.
         * 
         * @param prestamoRq detalles del préstamo.
         * @return respuesta del servicio.
         * @throws BadRequestException excepción.
         */
        @RequestMapping(value = "/guardar-prestamo", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.POST)
        ResponseEntity<PrestamoRs> guardarPrestamo(@RequestBody PrestamoRq prestamoRq)
                        throws BadRequestException;

        /**
         * Actualiza un préstamo existente.
         * 
         * @param prestamo detalles del préstamo a actualizar.
         * @return respuesta del servicio.
         * @throws BadRequestException excepción.
         */
        @RequestMapping(value = "/actualizar-prestamo", produces = { "application/json" }, consumes = {
                        "application/json" }, method = RequestMethod.POST)
        ResponseEntity<PrestamoRs> actualizarPrestamo(@RequestBody Prestamo prestamo)
                        throws BadRequestException;


}
>>>>>>> 708e27d9b1709601d41210363af62176929a3745
