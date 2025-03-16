/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador que implementa los métodos definidos en la interfaz UsuarioApi.
 * Proporciona los endpoints necesarios para gestionar usuarios, como listar usuarios
 * y obtener usuarios por correo electrónico.
 * 
 * @author Santiago
 */
@RestController
public class UsuarioApiController implements UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;
    
    /**
     * Método para listar todos los usuarios registrados en la base de datos.
     * 
     * @return Lista de usuarios.
     * @throws BadRequestException Si ocurre un error al procesar la solicitud.
     */
    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.listarUsuarios());
    }

    /**
     * Método para obtener un usuario por su correo electrónico.
     * 
     * @param usuarioCorreo El correo electrónico del usuario a obtener.
     * @return El usuario encontrado.
     * @throws BadRequestException Si no se encuentra el usuario con ese correo.
     */
    @Override
    public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(String usuarioCorreo) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.obtenerUsuarioCorreo(usuarioCorreo));
    }
}
