package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz para la gestión de usuarios en la API de la biblioteca.
 */
@RequestMapping("/usuario")
public interface UsuarioApi {
    
    /**
     * Lista todos los usuarios registrados en la base de datos.
     *
     * @return una respuesta HTTP con la lista de usuarios.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Usuario>> listarUsuarios();
    
    /**
     * Busca un usuario en la base de datos utilizando su correo electrónico.
     *
     * @param correo el correo electrónico del usuario a buscar.
     * @return una respuesta HTTP con un objeto Optional que contiene el usuario si se encuentra.
     * @throws BadRequestException si hay un error en la solicitud.
     */
    @GetMapping("/buscar-por-correo")
    ResponseEntity<Optional<Usuario>> buscarUsuarioPorCorreo(@RequestParam String correo) throws BadRequestException;
}
