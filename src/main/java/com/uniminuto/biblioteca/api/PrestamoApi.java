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

@CrossOrigin(origins = "*")
@RequestMapping("/prestamo")
public interface PrestamoApi {

    @RequestMapping(value = "/listar", method = RequestMethod.GET, produces = { "application/json" })
    ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException;

    @RequestMapping(value = "/guardar-prestamo", produces = { "application/json" }, consumes = {
            "application/json" }, method = RequestMethod.POST)
    ResponseEntity<PrestamoRs> guardarPrestamo(@RequestBody PrestamoRq prestamoRq) throws BadRequestException;

    @RequestMapping(value = "/actualizar-prestamo", produces = { "application/json" }, consumes = {
            "application/json" }, method = RequestMethod.POST)
    ResponseEntity<PrestamoRs> actualizarPrestamo(@RequestBody Prestamo prestamo) throws BadRequestException;
}
