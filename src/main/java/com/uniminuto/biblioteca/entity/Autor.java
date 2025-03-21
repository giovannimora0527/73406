package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import lombok.Data;
import java.util.List;

/**
 *
 * @author lmora
 */
@Data
@Entity
@Table(name = "autores")
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "nacionalidad", length = 50)
    private String nacionalidad;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libros;

    // Método agregado para permitir la asignación de ID manualmente
    public void setId(Integer id) {
        this.id = id;
    }
}
