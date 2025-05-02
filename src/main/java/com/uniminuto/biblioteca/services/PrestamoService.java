package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface PrestamoService {

    // Obtener todos los préstamos
    List<PrestamoRs> obtenerListadoPrestamos();  // CAMBIAR: List<Prestamo> -> List<PrestamoRs>

    // Obtener préstamo por ID
    PrestamoRs obtenerPrestamoPorId(Integer idPrestamo) throws BadRequestException;  // CAMBIAR: Prestamo -> PrestamoRs

    // Registrar nuevo préstamo
    PrestamoRs guardarPrestamo(PrestamoRq prestamo) throws BadRequestException;

    // Actualizar préstamo existente
    PrestamoRs actualizarPrestamo(PrestamoRq prestamo) throws BadRequestException;

    // Registrar devolución de libro
    PrestamoRs registrarDevolucion(Integer idPrestamo) throws BadRequestException;

    // Obtener préstamos de un usuario
    List<PrestamoRs> obtenerPrestamosPorUsuario(Integer idUsuario) throws BadRequestException;  // CAMBIAR

    // Obtener préstamos de un libro
    List<PrestamoRs> obtenerPrestamosPorLibro(Integer idLibro) throws BadRequestException;  // CAMBIAR

    // Obtener préstamos vencidos
    List<PrestamoRs> obtenerPrestamosVencidos(); // CAMBIAR
}
