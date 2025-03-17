package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;

/**
 * Controlador que implementa la interfaz "UsuarioApi".
 */
@RestController
public class UsuarioApiController implements UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Lista todos los usuarios.
     *
     * @return Una lista de usuarios.
     */
    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerUsuarios());
    }
    
    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo El correo electrónico del usuario a buscar.
     * @return Un objeto  que contiene el usuario si se encuentra.
     * @throws BadRequestException Si hay un error en la solicitud.
     */
    @Override
    public ResponseEntity<Optional<Usuario>> buscarUsuarioPorCorreo(String correo) throws BadRequestException {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorCorreo(correo));
    }
}