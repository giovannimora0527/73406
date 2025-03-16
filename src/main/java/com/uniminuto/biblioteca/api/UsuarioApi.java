/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author holma
 */
@CrossOrigin(origins = "*")
@RequestMapping("/biblioteca/usuarios")
public interface UsuarioApi {

    /**
     * Método para listar todos los usuarios registrados en la BD.
     *
     * @return Lista de usuarios.
     * @throws BadRequestException excepción.
     */
    @GetMapping(value = "/listar",
            produces = {"application/json"})
    ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException;

    /**
     * Método para obtener un usuario por su correo.
     *
     * @param correo Email del usuario.
     * @return Usuario correspondiente al correo.
     * @throws BadRequestException Excepción en caso de error.
     */
    @GetMapping(value = "/buscar",
            produces = {"application/json"})
    ResponseEntity<Optional<Usuario>> obtenerUsuarioPorCorreo(@RequestParam String correo)
            throws BadRequestException;
}