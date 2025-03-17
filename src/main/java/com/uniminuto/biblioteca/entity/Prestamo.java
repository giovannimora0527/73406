package com.uniminuto.biblioteca.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidad que representa un usuario en la biblioteca.
 * 
 * @author lmora
 */
@Data
@Entity
@Table(name = "usuarios")
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único del usuario */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    /** Nombre del usuario */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /** Correo electrónico del usuario (único) */
    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String correo;

    /** Número de teléfono del usuario */
    @Column(name = "telefono", length = 20)
    private String telefono;

    /** Fecha de registro del usuario */
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    /** Constructor para inicializar la fecha de registro automáticamente */
    @PrePersist
    protected void onCreate() {
        this.fechaRegistro = LocalDateTime.now();
    }
}
