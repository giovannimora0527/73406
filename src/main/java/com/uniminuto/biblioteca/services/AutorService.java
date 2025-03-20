package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Servicio para la gestión de autores.
 * 
 * Define los métodos necesarios para obtener información sobre los autores
 * desde la base de datos.
 * 
 * 
 * @author lmora
 */
public interface AutorService {
    
    /**
     * Obtiene una lista con todos los autores registrados.
     * 
     * @return Lista de autores.
     */
    List<Autor> obtenerListadoAutores();
    
    /**
     * Obtiene una lista de autores filtrados por su nacionalidad.
     * 
     * @param nacionalidad Nacionalidad de los autores a buscar.
     * @return Lista de autores con la nacionalidad especificada.
     * @throws BadRequestException Si ocurre un error en la consulta.
     */    
    List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad) throws BadRequestException;
    
    /**
     * Obtiene un autor específico por su identificador único (ID).
     * 
     * @param idAutor ID del autor a buscar.
     * @return Autor correspondiente al ID proporcionado.
     * @throws BadRequestException Si no se encuentra el autor.
     */    
    Autor obtenerAutorPorId(Long idAutor) throws BadRequestException;
}
