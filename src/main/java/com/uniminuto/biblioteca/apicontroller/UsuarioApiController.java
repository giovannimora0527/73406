/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

/**
 *
 * @author holma
 */
/**
 * Controlador REST para la gestión de usuarios en la biblioteca.
 * Proporciona endpoints para listar, buscar, guardar y eliminar usuarios.
 */
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioApiController {

    @Autowired
    private UsuarioService usuarioService;
    
    
    @GetMapping("/listarUsuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

     /**
     * Endpoint para obtener un usuario por su correo electrónico.
     * 
     * Este método busca un usuario en la base de datos utilizando su correo electrónico.
     * Antes de realizar la búsqueda, se valida el formato del correo. 
     * Si el formato es inválido, se devuelve un error 400 (BAD REQUEST).
     * Si no se encuentra el usuario, se devuelve un error 404 (NOT FOUND).
     *
     * @param correo Correo electrónico del usuario a buscar.
     * @return ResponseEntity con el usuario encontrado o un mensaje de error.
     */
    @GetMapping("/buscar-por-correo")
public ResponseEntity<?> buscarUsuarioPorCorreo(@RequestParam String correo) {
    try {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorCorreo(correo);
        
        // Si el usuario está presente, retornarlo con estado 200 OK
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            // Si no se encuentra, retornar 404 con un mensaje claro
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Collections.singletonMap("error", "Usuario no encontrado para el correo: " + correo));
        }
    } catch (IllegalArgumentException e) {
        // Si el correo tiene formato inválido, retornar 400 BAD REQUEST
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(Collections.singletonMap("error", "Formato de correo inválido: " + correo));
        }
    }
}