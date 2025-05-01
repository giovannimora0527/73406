/**
 * Servicio para gestionar las operaciones relacionadas con los préstamos de libros.
 * 
 * <p>Esta interfaz define los métodos necesarios para listar, crear y actualizar préstamos en el sistema
 * de biblioteca. Las implementaciones de esta interfaz deben proporcionar la lógica necesaria para
 * interactuar con la base de datos y manejar las reglas de negocio relacionadas con los préstamos.</p>
 * 
 * <p>El servicio incluye operaciones para listar todos los préstamos, guardar un nuevo préstamo
 * y actualizar un préstamo existente.</p>
 * 
 * @author Santiago
 * @version 1.0
 */
package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import org.apache.coyote.BadRequestException;
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

}
