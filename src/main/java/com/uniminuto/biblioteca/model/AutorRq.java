package com.uniminuto.biblioteca.model;

import java.time.LocalDate;
import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class AutorRq {
    private Integer autorId;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Integer nacionalidadId; 
  
}
