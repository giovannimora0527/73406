package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Prestamo.EstadoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
    
    
    
    // Buscar préstamos por ID de usuario
    List<Prestamo> findByUsuarioIdUsuario(Integer idUsuario);
    
    // Buscar préstamos por ID de libro
    List<Prestamo> findByLibroIdLibro(Integer idLibro);
    
    // Verificar si existe un préstamo activo para un libro
    boolean existsByLibroIdLibroAndEstado(Integer idLibro, EstadoPrestamo estado);
    
    // Buscar préstamos vencidos
    List<Prestamo> findByFechaDevolucionBeforeAndEstado(LocalDate fecha, EstadoPrestamo estado);
    
    // Contar préstamos activos de un usuario
    long countByUsuarioIdUsuarioAndEstado(Integer idUsuario, EstadoPrestamo estado);
}
