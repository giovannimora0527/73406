package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Libro.
 * Esta interfaz extiende JpaRepository para proporcionar operaciones CRUD
 * básicas para la entidad Libro. Además, incluye métodos personalizados
 * para realizar búsquedas específicas basadas en ciertos criterios.
 * 
 * @author Santiago
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {

    /**
     * Encuentra una lista de libros basada en el ID del autor.
     * 
     * @param autorId El identificador del autor.
     * @return Una lista de libros que pertenecen al autor con el ID proporcionado.
     */
    List<Libro> findByAutor_AutorId(Integer autorId);

    /**
     * Encuentra un libro por su título exacto, respetando mayúsculas y minúsculas.
     * 
     * @param titulo El título exacto del libro.
     * @return Un objeto Optional que contiene el libro si se encuentra,
     *         o un Optional vacío si no se encuentra.
     */
    Optional<Libro> findByTitulo(String titulo);
    
    /**
     * Encuentra una lista de libros cuyo año de publicación está en el rango
     * de los años proporcionados.
     * 
     * @param fechaInicio El año de inicio del rango.
     * @param fechaFin El año de fin del rango.
     * @return Una lista de libros publicados entre las fechas especificadas.
     */
    List<Libro> findByAnioPublicacionBetween(Integer fechaInicio, Integer fechaFin);
}
