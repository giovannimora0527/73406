package com.uniminuto.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "libros")
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer idLibro;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;  

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    @JsonBackReference
    private Autor autor;

    @Column(name = "anio_publicacion")
    private Integer anioPublicacion;

    @Column(name = "categoria", length = 100)
    private String categoria;

    @Column(name = "existencias", nullable = false)
    private Integer existencias;
}
