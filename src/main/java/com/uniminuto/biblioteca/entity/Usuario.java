/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author holma
 */@Entity
   @Table(name = "usuarios")
 
public class Usuario implements Serializable {
     /** Identificador único del usuario */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    
    /** Nombre completo del usuario */
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    /** Correo electrónico del usuario (debe ser único) */
    @Column(name = "correo", unique = true, nullable = false)
    private String correo;
    
    /** Numero de telefono del usuario */
    @Column(name = "telefono", nullable = false)
    private String telefono;
    
    /** Fecha de registro */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Date fechaRegistro;

    public Usuario() {
        this.fechaRegistro = new Date(); // Establece la fecha actual por defecto
    }

    public Usuario(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.fechaRegistro = new Date();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
