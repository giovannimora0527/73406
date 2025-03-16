/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * Clase que representa un Usuario en el sistema.
 * Esta clase mapea los datos de la tabla "usuarios" en la base de datos
 * y contiene información relevante sobre el usuario, como su identificador, nombre, correo,
 * teléfono y fecha de registro.
 * 
 * @author Santiago
 */
@Data
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    
    /** Identificador único del usuario (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    
    /** Nombre del usuario. */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    /** Correo del usuario. */
    @Column(name = "correo", nullable = false, length = 100)
    private String correo;
    
    /** Teléfono del usuario. */
    @Column(name = "telefono", nullable = false, length = 200)
    private String telefono;
    
    /** Fecha de registro del usuario. */
    @Column(name = "fecha_registro", nullable = false, length = 200)
    private String fechaRegistro;
}
