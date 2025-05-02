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
}
