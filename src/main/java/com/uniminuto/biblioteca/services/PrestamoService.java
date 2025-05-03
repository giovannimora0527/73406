<<<<<<< HEAD
=======

>>>>>>> 708e27d9b1709601d41210363af62176929a3745
package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import org.apache.coyote.BadRequestException;
<<<<<<< HEAD

import java.util.List;

public interface PrestamoService {

    // Obtener todos los préstamos
    List<PrestamoRs> obtenerListadoPrestamos();  // CAMBIAR: List<Prestamo> -> List<PrestamoRs>

    // Obtener préstamo por ID
    PrestamoRs obtenerPrestamoPorId(Integer idPrestamo) throws BadRequestException;  // CAMBIAR: Prestamo -> PrestamoRs

    // Registrar nuevo préstamo
    PrestamoRs registrarPrestamo(PrestamoRq prestamo) throws BadRequestException;

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
=======
import java.util.List;

/**
 * Interfaz que define las operaciones para gestionar los préstamos.
 */
public interface PrestamoService {

    /**
     * Lista todos los préstamos registrados en el sistema.
     * 
     * @return lista de préstamos
     * @throws BadRequestException si ocurre un error durante la consulta
     */
    List<Prestamo> listarTodos() throws BadRequestException;

    /**
     * Guarda un nuevo préstamo en el sistema.
     * 
     * @param prestamoRq datos del préstamo a guardar
     * @return respuesta con el mensaje de éxito o error de la operación
     * @throws BadRequestException si ocurre un error al guardar el préstamo
     */
    PrestamoRs guardarPrestamoNuevo(PrestamoRq prestamoRq) throws BadRequestException;

    /**
     * Actualiza un préstamo existente en el sistema.
     * 
     * @param prestamo datos del préstamo a actualizar
     * @return respuesta con el mensaje de éxito o error de la operación
     * @throws BadRequestException si ocurre un error al actualizar el préstamo
     */
    PrestamoRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException;

>>>>>>> 708e27d9b1709601d41210363af62176929a3745
}