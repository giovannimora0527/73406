package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author fx506
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId);
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    @Override
    public void eliminarUsuario(Integer usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }
}