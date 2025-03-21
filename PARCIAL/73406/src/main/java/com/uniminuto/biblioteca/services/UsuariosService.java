package com.uniminuto.biblioteca.services;

import java.util.List;
import com.uniminuto.biblioteca.entity.Usuario;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

/**
 *
 * @author pilol1111
 */

public interface UsuarioService {

    List<Usuario> listarUsuarios() throws BadRequestException;

    Usuario obtenerUsuarioId(Integer usuarioId) throws BadRequestException;

    Usuario obtenerUsuarioPorEmail(String correo) throws BadRequestException;
}
