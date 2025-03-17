package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends
        JpaRepository<Libro, Integer> {
    @Query("SELECT l FROM Libro l WHERE BINARY l.nombre = ?1")
    Optional<Libro> findByNombreExacto(String nombre);


    public Optional<Libro> findByNombre(String nombre);

    public List<Libro> findByFechaPublicacionBetween(String fechaInicio, String fechaFin);
    
}
