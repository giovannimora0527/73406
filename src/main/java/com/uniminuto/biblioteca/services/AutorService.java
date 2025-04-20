package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Servicio para la gestión de autores.
 * Proporciona métodos para listar, buscar, guardar y actualizar autores.
 * 
 */
public interface AutorService {

    /**
     *
     * @return
     * @throws BadRequestException
     */
    List<Autor> listarAutor() throws BadRequestException;
    /**
     * Servicio para listar todos los autores del sistema.
     * @return Lista de autores registrados.
     * @throws BadRequestException Excepción.
     */
    List<Autor> obtenerListadoAutores() throws BadRequestException;

    /**
     * Obtiene el listado de autores por nacionalidad.
     * @param nacionalidad Nombre de la nacionalidad.
     * @return Lista de autores filtrados por nacionalidad.
     * @throws BadRequestException Excepción.
     */
    List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad) throws BadRequestException;

    /**
     * Obtiene un autor por su ID.
     * @param autorId ID del autor.
     * @return Autor.
     * @throws BadRequestException Excepción.
     */
    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;

    /**
     * Guarda un nuevo autor en el sistema.
     * @param autor Datos del autor a guardar.
     * @return Respuesta con los datos del autor guardado.
     * @throws BadRequestException Excepción.
     */
    AutorRs guardarAutorNuevo(AutorRq autor) throws BadRequestException;

    /**
     * Actualiza los datos de un autor existente.
     * @param autor Datos del autor a actualizar.
     * @return Respuesta con los datos del autor actualizado.
     * @throws BadRequestException Excepción.
     */
    AutorRs actualizarAutor(Autor autor) throws BadRequestException;
}