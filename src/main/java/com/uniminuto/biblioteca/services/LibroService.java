package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * * Interfaz que define los servicios para la gestión de libros.
 * @author lmora
 */
public interface LibroService {
        
    /**
     * Lista todos los libros disponibles en la biblioteca.
     * 
     * @return una lista de libros.
     * @throws BadRequestException si ocurre un error al obtener los libros.
     */
    List<Libro> listarLibros() throws BadRequestException;
    
    /**
     * Obtiene un libro por su ID.
     * 
     * @param libroId el ID del libro a buscar.
     * @return el libro encontrado.
     * @throws BadRequestException si el libro no existe o hay un error en la búsqueda.
     */
    Libro obtenerLibroId(Integer libroId) throws BadRequestException;
    
    /**
     * Lista los libros asociados a un autor específico.
     * 
     * @param idAutor el ID del autor cuyos libros se desean listar.
     * @return una lista de libros del autor.
     * @throws BadRequestException si el autor no existe o no tiene libros registrados.
     */

    List<Libro> listarLibrosPorAutor(Long idAutor) throws BadRequestException;
    
    /**
     * Obtiene un libro a partir de su título.
     * 
     * @param titulo el título del libro a buscar.
     * @return el libro encontrado.
     * @throws BadRequestException si el libro no existe o hay un error en la búsqueda.
     */
    Libro obtenerLibroPorTitulo(String titulo) throws BadRequestException;
    
    /**
     * Lista los libros publicados dentro de un rango de años.
     * 
     * @param anioInicio el año de inicio del rango.
     * @param anioFin el año de fin del rango.
     * @return una lista de libros publicados dentro del rango especificado.
     * @throws BadRequestException si hay un error en la búsqueda o si los parámetros son inválidos.
     */
    List<Libro> listarLibrosPoranioPublicacion(int anioInicio, int anioFin) throws BadRequestException;
   
}
