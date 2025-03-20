/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Entidad que representa a un usuario en la base de datos.
 * 
 * La clase {@code Usuario} almacena información sobre los usuarios 
 * registrados en el sistema, incluyendo su nombre, correo, teléfono 
 * y fecha de registro.
 * 
 * Se utiliza JPA para la persistencia en la base de datos.
 * 
 * @author holma
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
     /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;
    /**
     * Nombre del usuario.
     */

    @Column(name = "nombre")
    private String nombre;
    /**
     * Correo electrónico del usuario.
     */

    @Column(name = "correo")
    private String correo;
    /**
     * Número de teléfono del usuario.
     */

    @Column(name = "telefono")
    private String telefono;
    /**
     * Fecha y hora de registro del usuario en el sistema.
     */

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    // Getters y Setters

    /**
     * Obtiene el ID del usuario.
     * @return 
     * */
    public Long getIdUsuario() {
        return idUsuario;
    }
    /**
     * Establece el ID del usuario.
     * 
     * @param idUsuario Nuevo ID del usuario.
     */

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    /**
     * Obtiene el nombre del usuario.
     * 
     * @return Nombre del usuario.
     */

    public String getNombre() {
        return nombre;
    }
    /**
     * Establece el nombre del usuario.
     * 
     * @param nombre Nuevo nombre del usuario.
     */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Obtiene el correo electrónico del usuario.
     * 
     * @return Correo del usuario.
     */

    public String getCorreo() {
        return correo;
    }
    
    /**
     * Establece el correo electrónico del usuario.
     * 
     * @param correo Nuevo correo del usuario.
     */

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    /**
     * Obtiene el número de teléfono del usuario.
     * 
     * @return Teléfono del usuario.
     */

    public String getTelefono() {
        return telefono;
    }
    /**
     * Establece el número de teléfono del usuario.
     * 
     * @param telefono Nuevo teléfono del usuario.
     */

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    /**
     * Obtiene la fecha y hora de registro del usuario.
     * 
     * @return Fecha de registro del usuario.
     */

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    /**
     * Establece la fecha y hora de registro del usuario.
     * 
     * @param fechaRegistro Nueva fecha de registro.
     */

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}