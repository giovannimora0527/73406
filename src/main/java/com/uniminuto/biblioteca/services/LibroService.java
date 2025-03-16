package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;


/**
 *
 * @author lmora
 */
public interface LibroService {
    /** 
     * Lista todos los libros disponibles en la biblioteca.
     * @return Lista de libros.
     */
    List<Libro> listarLibros();

    /** 
     * Obtiene un libro por su ID.
     * @param libroId ID del libro.
     * @return El libro correspondiente al ID.
     * @throws IllegalArgumentException si el ID es inválido.
     */
    Libro obtenerLibroId(Integer libroId) throws IllegalArgumentException;

    /** 
     * Lista los libros asociados a un autor por su ID.
     * @param autorId ID del autor.
     * @return Lista de libros asociados al autor.
     * @throws IllegalArgumentException si el ID del autor no existe.
     */
    List<Libro> listarLibrosPorAutor(Integer autorId) throws IllegalArgumentException;
    
    /**
     * Obtiene un libro por su título exacto (case-sensitive).
     * 
     * @param titulo El título exacto del libro.
     * @return El libro encontrado.
     */
    Libro obtenerLibroPorTitulo(String titulo);
    
    
    
    // Método para listar libros por un rango de fechas de publicación
    List<Libro> listarLibrosPorAnioPublicacion(Integer anioInicio, Integer anioFin);
}
