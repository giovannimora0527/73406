package com.uniminuto.biblioteca.api;

import java.util.List;
import com.uniminuto.biblioteca.entity.Autor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API para gestionar autores en la biblioteca.
 * Permite listar autores, filtrarlos por nacionalidad y obtener detalles de un autor por su ID.
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/autor")
public interface AutorApi {

    /**
     * Lista todos los autores registrados en la base de datos.
     *
     * @return Lista de autores.
     * @throws BadRequestException si ocurre un error en la consulta.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Autor>> listarAutores() throws BadRequestException;

    /**
     * Lista autores filtrados por nacionalidad.
     *
     * @param nacionalidad Nacionalidad del autor.
     * @return Lista de autores con la nacionalidad especificada.
     * @throws BadRequestException si ocurre un error en la consulta.
     */
    @GetMapping("/listar-nacionalidad")
    ResponseEntity<List<Autor>> listarAutoresByNacionalidad(
            @RequestParam String nacionalidad) throws BadRequestException;

    /**
     * Obtiene los datos de un autor por su ID.
     *
     * @param id ID del autor.
     * @return Datos del autor correspondiente.
     * @throws BadRequestException si el autor no existe o hay un error en la consulta.
     */
    @GetMapping("/autorId")
    ResponseEntity<Autor> listarAutorPorId(@RequestParam("id") Integer id) throws BadRequestException;
}

