package com.uniminuto.biblioteca.repository;


import com.uniminuto.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends
        JpaRepository<Libro, Integer> {

    // Query para listar todos los libros
    @Query("SELECT l.titulo FROM Libro l JOIN Autor a ON l.autorId = a.autorId WHERE a.autorId = :autorId")
    List<Object> findAllBooksByAutorId(Integer autorId);


    // Query para buscar un libro por su titulo
    @Query("SELECT l FROM Libro l WHERE function('BINARY', l.titulo) = function('BINARY', :tituloLibro)")
    Libro findByTitulo(String tituloLibro);


    @Query("SELECT l FROM Libro l WHERE (l.anioPublicacion IS NOT NULL) AND (l.anioPublicacion BETWEEN :fechaInicio AND :fechaFin)")
    List<Libro> findByRangoFechas(Integer fechaInicio, Integer fechaFin);

    // @Query("SELECT l FROM Libro l WHERE l.anioPublicacion IS NOT NULL AND l.anioPublicacion >= :fechaInicio AND l.anioPublicacion <= :fechaFin")
    // List<Libro> findByRangoFechas(Integer fechaInicio, Integer fechaFin);
}
