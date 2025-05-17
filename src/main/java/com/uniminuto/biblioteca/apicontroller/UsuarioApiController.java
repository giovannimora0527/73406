package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.UsuarioApi;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UsuarioApiController implements UsuarioApi {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.listarTodo());
    }

    @Override
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(String correo) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.buscarPorCorreo(correo));
    }

    @Override
    public ResponseEntity<UsuarioRs> guardarUsuario(UsuarioRq usuario) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.guardarUsuarioNuevo(usuario));
    }

    @Override
    public ResponseEntity<UsuarioRs> actualizarUsuario(Usuario usuario) throws BadRequestException {
        return ResponseEntity.ok(this.usuarioService.actualizarUsuario(usuario));
    }

    // NUEVO: carga masiva CSV
    @Override
    public ResponseEntity<UsuarioRs> cargaMasivaUsuarios(MultipartFile file) throws BadRequestException {
        if (file.isEmpty()) {
            throw new BadRequestException("El archivo CSV está vacío");
        }
        UsuarioRs response = this.usuarioService.cargaMasivaUsuarios(file);
        return ResponseEntity.ok(response);
    }
}
