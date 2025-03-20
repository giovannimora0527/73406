package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.services.UsuarioService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author lmora
 */
@RestController
public class UsuarioApiController implements UsuarioApi {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.listarUsuarios());
    }

    @Override
    public ResponseEntity<Usuario> obtenerUsuarioPorId(Integer UsuarioId) throws BadRequestException {
      return ResponseEntity.ok(this.usuarioService.obtenerUsuarioId(UsuarioId));
    }
    
    @Override
    public ResponseEntity<Usuario> obtenerUsuarioEmail(String correo) throws BadRequestException {
      return ResponseEntity.ok(this.usuarioService.obtenerUsuarioEmail(correo));
    }
}