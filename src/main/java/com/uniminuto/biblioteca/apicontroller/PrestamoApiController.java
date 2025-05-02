package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.services.PrestamoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    @Override
    public ResponseEntity<List<PrestamoRs>> listarPrestamos() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerListadoPrestamos());
    }

    @Override
    public ResponseEntity<PrestamoRs> obtenerPrestamoPorId(Integer idPrestamo) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerPrestamoPorId(idPrestamo));
    }

     @Override
    public ResponseEntity<PrestamoRs> guardarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.guardarPrestamo(prestamoRq));
    }

    @Override
    public ResponseEntity<PrestamoRs> actualizarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.actualizarPrestamo(prestamoRq));
    }

    @Override
    public ResponseEntity<PrestamoRs> registrarDevolucion(Integer idPrestamo) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.registrarDevolucion(idPrestamo));
    }

    @Override
    public ResponseEntity<List<PrestamoRs>> listarPrestamosPorUsuario(Integer idUsuario) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerPrestamosPorUsuario(idUsuario));
    }

    @Override
    public ResponseEntity<List<PrestamoRs>> listarPrestamosPorLibro(Integer idLibro) throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerPrestamosPorLibro(idLibro));
    }

    @Override
    public ResponseEntity<List<PrestamoRs>> listarPrestamosVencidos() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.obtenerPrestamosVencidos());
    }

}
