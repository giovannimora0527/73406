package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    // Buscar libro por título exacto (sensitivo a mayúsculas y minúsculas)
    @Query("SELECT l FROM Libro l WHERE l.titulo = ?1")
    Optional<Libro> findByTituloExacto(String titulo);

    // : Método debe buscar por título
    Optional<Libro> findByTitulo(String titulo);


    List<Libro> findByAnioPublicacionBetween(Integer anioInicio, Integer anioFin);

    // Buscar libros por ID del autor
    @Query("SELECT l FROM Libro l WHERE l.autor.id = :autorId")
    List<Libro> findLibrosByAutorId(@Param("autorId") Integer autorId);
}
