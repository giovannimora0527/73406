<<<<<<< HEAD
package com.uniminuto.biblioteca.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PrestamoRs {
    private Integer idPrestamo;
    private UsuarioSimple usuario;
    private LibroSimple libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private LocalDate fechaEntrega;
    private String estado;
    private String message;

    @Data
    public static class UsuarioSimple {
        private Integer idUsuario;
        private String nombre;
    }

    @Data
    public static class LibroSimple {
        private Integer idLibro;
        private String titulo;
    }
=======

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
>>>>>>> 708e27d9b1709601d41210363af62176929a3745
}