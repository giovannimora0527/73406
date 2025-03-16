/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Interfaz que define los métodos de la API para la gestión de usuarios.
 * Permite realizar operaciones como listar usuarios y obtener usuarios por correo.
 * 
 * @author Santiago
 */
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public interface UsuarioApi {

    /**
     * Método para listar todos los usuarios registrados en la base de datos.
     * 
     * @return Lista de usuarios.
     * @throws BadRequestException Si ocurre algún error al procesar la solicitud.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException;
    
    /**
     * Método para obtener un usuario por su correo electrónico.
     * 
     * @param usuarioCorreo El correo electrónico del usuario a obtener.
     * @return El usuario encontrado.
     * @throws BadRequestException Si no se encuentra el usuario con ese correo.
     */
    @RequestMapping(value = "/obtener-usuario-correo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> obtenerUsuarioPorCorreo(@RequestParam String usuarioCorreo)
            throws BadRequestException;
}
