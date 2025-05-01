/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author santiago
 */
/**
 * Implementación del API para las operaciones sobre préstamos.
 */
@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @Override
    public ResponseEntity<PrestamoRs> guardarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        PrestamoRs response = prestamoService.guardarPrestamoNuevo(prestamoRq);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PrestamoRs> actualizarPrestamo(Prestamo prestamo) throws BadRequestException {
        PrestamoRs response = prestamoService.actualizarPrestamo(prestamo);
        return ResponseEntity.ok(response);
    }

}
