package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;

import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface AutorService {
    List<Autor> obtenerListadoAutores();
    List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad) throws BadRequestException;
    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;
    // Agregamos este método para evitar el error en la implementación
    List<Libro> obtenerLibrosPorAutor(Integer autorId) throws BadRequestException;
}
