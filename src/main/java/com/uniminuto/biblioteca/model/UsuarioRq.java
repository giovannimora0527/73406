package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author Michael Conde
 */
@Data
public class UsuarioRq {
   private String nombre; 
   private String correo;
   private String telefono;
}
