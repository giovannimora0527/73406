package com.uniminuto.biblioteca.model;

import lombok.Data;

/**
 *
 * @author lmora
 */
@Data
public class UsuarioRq {
   private String nombreCompleto; 
   private String correo;
   private String telefono;
}
