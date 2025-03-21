package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


/**
 * Entidad que representa a un Autor.
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    /**
     * Buscar un libro por su título con distinción de mayúsculas y minúsculas.
     * @param titulo Título exacto del libro.
     * @return Libro encontrado, si existe.
     */
    Optional<Libro> findByTitulo(String titulo);

    /**
     * Buscar libros por el ID del autor.
     * @param id ID del autor.
     * @return Lista de libros del autor.
     */
    List<Libro> findByAutor_Id(Integer id);

    /**
     * Buscar libros dentro de un rango de fechas de publicación.
     * @param fechaInicio Año de inicio.
     * @param fechaFin Año de fin.
     * @return Lista de libros en el rango de fechas.
     */
    @Query("SELECT l FROM Libro l WHERE l.anioPublicacion IS NOT NULL AND (l.anioPublicacion BETWEEN :fechaInicio AND :fechaFin)")
    List<Libro> findByRangoFechas(@Param("fechaInicio") Integer fechaInicio, @Param("fechaFin") Integer fechaFin);
}
