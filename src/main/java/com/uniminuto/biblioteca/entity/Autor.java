package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Clase que representa un Autor en el sistema.
 * Esta clase se mapea a la tabla "autores" en la base de datos.
 * Contiene información sobre un autor, como su identificador, nombre,
 * nacionalidad y fecha de nacimiento.
 * 
 * @author Santiago
 */
@Data
@Entity
@Table(name = "autores")
public class Autor implements Serializable {
    
    /**
     * Versión serializable del objeto.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador único del autor.
     * Este campo se utiliza como clave primaria en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Integer autorId;
    
    /**
     * Nombre del autor.
     * Este campo no puede ser nulo y tiene una longitud máxima de 100 caracteres.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    /**
     * Nacionalidad del autor.
     * Este campo tiene una longitud máxima de 50 caracteres.
     */
    @Column(name = "nacionalidad", length = 50)
    private String nacionalidad;
    
    /**
     * Fecha de nacimiento del autor.
     * Representa la fecha en que el autor nació.
     */
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
}
