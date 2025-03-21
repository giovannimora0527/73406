package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uniminuto.biblioteca.api.UsuarioApi;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author fx506
 */
@RestController

public class UsuarioApiController implements UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;

    private static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    @GetMapping("/listar") 
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @Override
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontró un usuario con el ID: " + id));
    }

    @Override
    public ResponseEntity<Usuario> obtenerUsuarioPorCorreo(@RequestParam String correo) {
        if (!Pattern.matches(REGEX_EMAIL, correo)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El formato del correo electrónico no es válido.");
        }

        return usuarioService.obtenerUsuarioPorCorreo(correo)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontró un usuario con el correo: " + correo));
    }
}