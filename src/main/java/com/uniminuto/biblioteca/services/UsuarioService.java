package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author fx506
 */
public interface UsuarioService {
    List<Usuario> listarUsuarios();
    Optional<Usuario> obtenerUsuarioPorId(Integer usuarioId);
    Optional<Usuario> obtenerUsuarioPorCorreo(String correo);
    void eliminarUsuario(Integer usuarioId);
}