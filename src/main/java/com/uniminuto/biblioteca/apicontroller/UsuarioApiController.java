/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


/**
 *
 * @author holma
 */

@RestController
public class UsuarioApiController implements UsuarioApi {

    /**
     * UsuarioService.
     */
    @Autowired
    private UsuarioService usuarioService;
    
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    /**
     * Lista todos los usuarios registrados en la biblioteca.
     *
     * @return Lista de usuarios en la base de datos.
     * @throws ResponseStatusException Si ocurre un error en la consulta.
     */
    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            
            if (usuarios.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No hay usuarios registrados.");
            }
            
            return ResponseEntity.ok(usuarios);

        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error al obtener la lista de usuarios", e);
        }
    }
    
    /**
    * Obtiene un usuario por su correo electrónico.
    *
    * @param correo Correo electrónico del usuario a buscar.
    * @return ResponseEntity con el usuario encontrado o un error si no existe.
    */
   @GetMapping("/buscar-por-correo")
    @Override
   public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(@RequestParam String correo) {
       Usuario usuario = usuarioService.obtenerUsuarioPorCorreo(correo)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, 
                       "Usuario con correo " + correo + " no encontrado."));

       return ResponseEntity.ok(usuario);
   }
}
 
