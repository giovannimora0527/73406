package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    
    List<Libro> findByAutor_AutorId(Integer autorId);
    
    /**
     * Busca un libro por su título.
     * 
     * @param titulo Título del libro a buscar..
     */
    Optional<Libro> findByTitulo(String titulo);
    
    /**
     * Busca libros publicados dentro de un rango de años.
     * 
     * @param anioInicio Año de inicio del rango.
     * @param anioFin Año de fin del rango.
     * @return Lista de libros publicados entre los años especificados.
     */
    List<Libro> findByAnioPublicacionBetween(Integer anioInicio, Integer anioFin);
}