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
 * Interfaz que define los endpoints para la gestión de usuarios.
 * Proporciona métodos para listar usuarios y buscar un usuario por correo electrónico.
 * 
 * @author holma
 */
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
public interface UsuarioApi {

    /**
     * Método para listar todos los usuarios registrados en la BD.
     *
     * @return Lista de usuarios.
     * @throws BadRequestException excepción.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException;
    
    
    /**
     * Método para obtener un usuario a partir de su correo electrónico.
     *
     * @param correo el correo electrónico del usuario a buscar.
     * @return ResponseEntity con el usuario encontrado o un mensaje de error si no existe.
     */
    
    @RequestMapping(value = "/buscar-por-correo",
        produces = {"application/json"},
        consumes = {"application/json"},
        method = RequestMethod.GET)
    ResponseEntity<?> obtenerUsuarioPorCorreo(@RequestParam String correo);
}
