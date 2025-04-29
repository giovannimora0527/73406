package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface PrestamoService {
    
    // Obtener todos los préstamos
    List<Prestamo> obtenerListadoPrestamos();
    
    // Obtener préstamo por ID
    Prestamo obtenerPrestamoPorId(Integer idPrestamo) throws BadRequestException;
    
    // Registrar nuevo préstamo
    PrestamoRs registrarPrestamo(PrestamoRq prestamo) throws BadRequestException;
    
    // Actualizar préstamo existente
    PrestamoRs actualizarPrestamo(PrestamoRq prestamo) throws BadRequestException;
    
    // Registrar devolución de libro
    PrestamoRs registrarDevolucion(Integer idPrestamo) throws BadRequestException;
    
    // Obtener préstamos de un usuario
    List<Prestamo> obtenerPrestamosPorUsuario(Integer idUsuario) throws BadRequestException;
    
    // Obtener préstamos de un libro
    List<Prestamo> obtenerPrestamosPorLibro(Integer idLibro) throws BadRequestException;
    
    // Obtener préstamos vencidos
    List<Prestamo> obtenerPrestamosVencidos();
}
