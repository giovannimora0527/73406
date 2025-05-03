
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