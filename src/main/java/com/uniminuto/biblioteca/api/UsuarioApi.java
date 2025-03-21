
package com.uniminuto.biblioteca.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.uniminuto.biblioteca.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 * @author lmora
 */

@RequestMapping("/usuarios") 
public interface UsuarioApi {

    /**
     * Método para listar los usuarios registrados en la BD.
     *
     * @return Lista de usuarios.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Usuario>> listarUsuarios();

    /**
     * Método para obtener un usuario por su ID.
     *
     * @param id ID del usuario.
     * @return Datos del usuario.
     */
    @GetMapping("/listar/{id}")  // Aquí corregimos la ruta
    ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer id);

    /**
     * Método para obtener un usuario por su correo electrónico.
     *
     * @param correo Correo del usuario.
     * @return Datos del usuario.
     */
    @GetMapping("/buscar")
    ResponseEntity<Usuario> obtenerUsuarioPorCorreo(@RequestParam String correo);
}