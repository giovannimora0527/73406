package com.uniminuto.biblioteca.services;

import java.util.List;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface LibroService {
    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    List<Libro> listarLibros() throws BadRequestException;

    /**
     * Metodo para obtener un libro por su id.
     *
     * @param libroId Id del libro.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    Libro obtenerLibroId(Integer libroId) throws BadRequestException;

    /**
     * Metodo para listar los libros registrado por AutorId.
     * @return Lista de libros por Id.
     * @throws BadRequestException excepcion.
     */
    List<Object> listarLibrosPorIdAutor(Integer autorId) throws BadRequestException;

    /**
     * Metodo para buscar un libro por su titulo.
     * @param tituloLibro Titulo del libro.
     * @return Libro.
     * @throws BadRequestException excepcion.
     */
    Libro buscarPorTitulo(String tituloLibro) throws BadRequestException;

    /**
     * Metodo para buscar un libro por rango de fechas.
     * @param fechaInicio Fecha de inicio.
     * @param fechaFin Fecha de fin.
     * @return Lista de libros.
     * @throws BadRequestException excepcion.
     */
    List<Libro> buscarPorRangoFechas(Integer fechaInicio, Integer fechaFin) throws BadRequestException;
}
