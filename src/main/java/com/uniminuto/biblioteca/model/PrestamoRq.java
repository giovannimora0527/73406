/**
 * Representa una solicitud de préstamo de un libro en el sistema de biblioteca.
 * Esta clase contiene los datos necesarios para registrar un préstamo.
 * 
 * <p>Incluye información sobre el usuario que realiza el préstamo,
 * el libro solicitado, la fecha del préstamo y la fecha esperada de devolución.</p>
 * 
 * <p>Usada como objeto de transferencia de datos (DTO) entre el cliente y el servidor.</p>
 * 
 * @author kaleth
 * @version 1.0
 */
package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Clase que representa la solicitud de préstamo de un libro.
 */
@Data
public class PrestamoRq {

    /**
     * Identificador del usuario que realiza el préstamo.
     */
    private Integer idUsuario;

    /**
     * Identificador del libro a prestar.
     */
    private Integer idLibro;

    /**
     * Fecha en que se realiza el préstamo.
     */
    private LocalDateTime fechaPrestamo;

    /**
     * Fecha esperada para la devolución del libro.
     */
    private LocalDateTime fechaDevolucion;
}