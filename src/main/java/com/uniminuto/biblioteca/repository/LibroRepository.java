package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Libro.
 * Proporciona métodos para interactuar con la base de datos.
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    /**
     * Busca libros por el nombre del autor.
     * @param nombreAutor Nombre del autor.
     * @return Lista de libros escritos por ese autor.
     */
    List<Libro> findByAutor_Nombre(String nombreAutor);

    
    
    /**
     * Busca libros por el ID del autor.
     * @param autorId ID del autor.
     * @return Lista de libros asociados al autor.
     */
    List<Libro> findByAutor_AutorId(Integer autorId);

    
    
    /**
     * Busca un libro por su título exacto (case-sensitive).
     * 
     * @param titulo El título exacto del libro.
     * @return Un Optional que contiene el libro si existe, de lo contrario vacío.
     */
    Optional<Libro> findByTitulo(String titulo); // Debe devolver un Optional
   
    
    
    /**
     * Verifica si existen libros asociados a un autor por su ID.
     * @param autorId ID del autor.
     * @return `true` si existen libros asociados, `false` si no.
     */
    boolean existsByAutor_AutorId(Integer autorId);

    /**
     * Método para buscar libros en un rango de fechas de publicación.
     * @param anioInicio Año de inicio del rango.
     * @param anioFin Año de fin del rango.
     * @return Lista de libros publicados dentro del rango de años.
     */
    List<Libro> findByAnioPublicacionBetween(Integer anioInicio, Integer anioFin);
}