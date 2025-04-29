package com.uniminuto.biblioteca.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa un préstamo de libro en la biblioteca
 */
@Data
@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;
    
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDateTime fechaPrestamo;
    
    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;
    
    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado = EstadoPrestamo.PRESTADO;
    
    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;
    
    public enum EstadoPrestamo {
        PRESTADO,
        DEVUELTO
    }
}
