package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
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
public interface LibroRepository extends
        JpaRepository<Libro, Integer> {
    
    /**
     * Obtiene la lista dado un autor.
     * @param autor Autor a buscar.
     * @return Lista de libros.
     */
    List<Libro> findByAutor(Autor autor);
    
    /**
     * Busca un libro por su nombre.
     * @param nombreLibro Nombre del libro a buscar.
     * @return Libro.
     */
    Libro findByTitulo(String nombreLibro);
    
    /**
     * Lista los libros por rango de fecha de publicacion.
     * @param anioIni Año inicial.
     * @param anioFin Año final.
     * @return Lista de libros que cumplen el criterio.
     */
    List<Libro> findByAnioPublicacionBetween(Integer anioIni, Integer anioFin);
    
    
    /**
     * Encuentra libros con existencias mayores que cero
     * @param existencias Número mínimo de existencias
     * @return Lista de libros con existencias disponibles
     */
    List<Libro> findByExistenciasGreaterThan(int existencias);
    
    
    Libro findByTituloAndAutor(String titulo, Autor autor);
    
    /**
 * Busca libros por título (puede devolver múltiples resultados)
 * @param titulo el título del libro
 * @return lista de libros que coinciden con el título
 */
List<Libro> findByTituloIgnoreCase(String titulo);

/**
 * Verifica si existe un libro con el título y autor específicos
 * @param titulo el título del libro
 * @param autor el autor del libro
 * @return true si existe, false en caso contrario
 */
boolean existsByTituloIgnoreCaseAndAutor(String titulo, Autor autor);

/**
 * Busca un libro por título exacto y autor
 * @param titulo el título del libro
 * @param autor el autor del libro
 * @return el libro si existe, null en caso contrario
 */
Optional<Libro> findByTituloIgnoreCaseAndAutor(String titulo, Autor autor);


/**
     * MÉTODO ALTERNATIVO: Verifica si existe un libro con el mismo título (ignorando mayúsculas/minúsculas)
     * @param titulo Título del libro a verificar
     * @return true si existe un libro con ese título, false en caso contrario
     */
    boolean existsByTituloIgnoreCase(String titulo);
    
    
}
