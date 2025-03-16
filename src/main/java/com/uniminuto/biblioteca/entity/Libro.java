package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * Clase que representa un Libro en el sistema.
 * Esta clase mapea los datos de la tabla "libros" en la base de datos
 * y contiene información relevante sobre el libro, como su título, autor, año de publicación, categoría y existencias.
 * 
 * @author Santiago
 */
@Data
@Entity
@Table(name = "libros")
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del libro.
     * Este campo es la clave primaria de la tabla "libros" en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer idLibro;

    /**
     * Título del libro.
     * Este campo no puede ser nulo y tiene una longitud máxima de 200 caracteres.
     */
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    /**
     * Autor del libro.
     * Este campo es una clave foránea que referencia a la entidad Autor.
     * La relación es de muchos libros para un solo autor.
     */
    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

    /**
     * Año de publicación del libro.
     * Este campo guarda el año en que se publicó el libro.
     */
    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    /**
     * Categoría a la que pertenece el libro.
     * Este campo tiene una longitud máxima de 100 caracteres.
     */
    @Column(name = "categoria", length = 100)
    private String categoria;

    /**
     * Cantidad de ejemplares disponibles del libro.
     * Este campo no puede ser nulo.
     */
    @Column(name = "existencias", nullable = false)
    private Integer existencias;
}
