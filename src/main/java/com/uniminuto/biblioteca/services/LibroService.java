package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;

public interface LibroService {
    List<Libro> listarLibros();
    
    Libro obtenerLibroId(Integer libroId);
    
     /**
     * Obtiene una lista de libros de acuerdo con el ID del autor.
     *
     * @param autorId Identificador único del autor.
     * @return Lista de libros escritos por el autor especificado.
     */
    List<Libro> obtenerLibrosPorAutor(Integer autorId);

    /**
     * Busca un libro por su título.
     *
     * @param titulo Título del libro a buscar.
     * @return El libro que coincida con el título proporcionado.
     */
    Libro obtenerLibroPorNombre(String titulo);

    /**
     * Obtiene una lista de libros publicados dentro de un rango de años.
     *
     * @param anioInicio Año inicial del rango de búsqueda.
     * @param anioFin Año final del rango de búsqueda.
     * @return Lista de libros publicados dentro del rango de años especificado.
     */
    List<Libro> obtenerLibrosPorRangoAnios(Integer anioInicio, Integer anioFin);
}