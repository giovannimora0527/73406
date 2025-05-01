/**
 * Representa la respuesta de una operación de préstamo en el sistema de biblioteca.
 * 
 * <p>Esta clase contiene el mensaje que se devuelve como respuesta después de
 * realizar una acción relacionada con un préstamo, como crear o actualizar un préstamo.</p>
 * 
 * <p>Es utilizada para enviar información al usuario sobre el resultado de la operación.</p>
 * 
 * @author Santiago
 * @version 1.0
 */
package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 * Clase que contiene el mensaje de respuesta para las operaciones de préstamo.
 */
@Data
public class PrestamoRs {

    /**
     * Mensaje que indica el resultado de la operación.
     */
    private String message;
}
