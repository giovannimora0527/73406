package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface UsuarioService {
    List<Usuario> listarUsuarios() throws BadRequestException;
    
    Usuario obtenerUsuarioId(Integer usuarioId) throws BadRequestException;
    
    Usuario obtenerUsuarioEmail(String email) throws BadRequestException; 
}