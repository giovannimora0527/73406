package com.uniminuto.biblioteca.entity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
/**
 *
 * @author fx506
 */
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    private static final long serialVersionUID = 1L;

    /** Identificador único del usuario (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /** Nombre completo del usuario. */
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    /** Correo electrónico del usuario. */
    @Column(name = "correo", nullable = false, length = 150, unique = true)
    private String correo;

    /** Teléfono del usuario. */
    @Column(name = "telefono", length = 20)
    private String telefono;

    /** Dirección del usuario. */
    @Column(name = "direccion", length = 255)
    private String direccion;
}   
