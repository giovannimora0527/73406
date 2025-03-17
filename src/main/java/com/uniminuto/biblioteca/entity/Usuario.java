package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import lombok.Data;

/**
 * Representa la entidad Usuario en la base de datos.
 * Contiene la información básica de un usuario registrado en la biblioteca.
 * Implementa {@link Serializable} para permitir la serialización del objeto.
 * 
 * Se usa {@link Data} de Lombok para generar automáticamente 
 * los métodos getter, setter, equals, hashCode y toString.
 * 
 * La tabla en la base de datos se llama "usuarios".
 * 
 * @author 1031794_sofía_pedraza
 */
@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador único del usuario en la base de datos.
     * Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer usuarioId;
    
    /**
     * Nombre del usuario.
     * No puede ser nulo y tiene un límite de 100 caracteres.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    /**
     * Correo electrónico del usuario.
     * Debe tener un formato válido y es único en la base de datos que se valida con la anotación {@link Email}.
     */
    @Email 
    @Column(name = "correo", unique = true, length = 50)
    private String correo;
    
    /**
     * Número de teléfono del usuario.
     * Puede ser nulo y tiene un límite de 15 caracteres.
     */
    @Column(name = "telefono", length = 15)
    private String telefono;
    
    /**
     * Fecha en la que el usuario se registró en la biblioteca.
     * No puede ser nula.
     */
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;
}
