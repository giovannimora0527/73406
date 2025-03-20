package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends
        JpaRepository<Libro, Integer> {
    // Método para buscar libros por el ID del autor
    List<Libro> findByAutor(Autor autor);
    
    // Buscar un libro por título (nombre en la BD)
    Optional<Libro> findByTitulo(String titulo);
    
    
    // Buscar libros por rango Anio Publicacion)
    List<Libro> findByAnioPublicacionBetween(int anioInicio, int anioFin);

}