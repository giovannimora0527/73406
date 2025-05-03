package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PrestamoRq {

    private Integer idUsuario;
    private Integer idLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
}
