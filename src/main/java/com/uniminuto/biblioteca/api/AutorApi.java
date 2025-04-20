package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * API para la gestión de autores.
 */
@CrossOrigin(origins = "*")
@RequestMapping("/autor")
public interface AutorApi {

    /**
     * Lista todos los autores registrados.
     * @return Lista de autores.
     * @throws BadRequestException Excepción.
     */
    @RequestMapping(
            value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutores() throws BadRequestException;

    /**
     * Lista autores filtrados por nacionalidad.
     * @param nacionalidad Nacionalidad del autor.
     * @return Lista de autores.
     * @throws BadRequestException Excepción.
     */
    @RequestMapping(
            value = "/obtener-nacionalidad",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Autor>> listarAutoresByNacionalidad(
            @RequestParam String nacionalidad) throws BadRequestException;

    /**
     * Obtiene un autor por su ID.
     * @param autorIds ID del autor.
     * @return Autor correspondiente.
     * @throws BadRequestException Excepción.
     */
    @RequestMapping(
            value = "/obtener-autor-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Autor> listarAutorPorId(@RequestParam Integer autorIds) throws BadRequestException;

    /**
     * Guarda un nuevo autor en la base de datos.
     * @param autor Datos del autor a registrar.
     * @return Resultado del guardado.
     * @throws BadRequestException Excepción.
     */
    @RequestMapping(
            value = "/guardar-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<AutorRs> guardarAutor(@RequestBody AutorRq autor) throws BadRequestException;

    /**
     * Actualiza los datos de un autor existente.
     * @param autor Datos actualizados del autor.
     * @return Resultado de la actualización.
     * @throws BadRequestException Excepción.
     */
    @RequestMapping(
            value = "/actualizar-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<AutorRs> actualizarAutor(@RequestBody Autor autor) throws BadRequestException;
}
