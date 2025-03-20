package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarUsuarios() throws BadRequestException {
       return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerUsuarioId(Integer usuarioId) throws BadRequestException {        
        Optional<Usuario> optUsuario = this.usuarioRepository.findById(usuarioId);
        if (!optUsuario.isPresent()) {
            throw new BadRequestException("No se encuentra el Usuariio con el id = " 
                    + usuarioId);
        }
      return optUsuario.get();
    }
    @Override
    public Usuario obtenerUsuarioEmail(String correo) throws BadRequestException {
        Optional<Usuario> optUsuario = this.usuarioRepository.findByCorreo(correo);

        if (!optUsuario.isPresent()) {
            throw new BadRequestException("No se encuentra el Usuario con el email = " + correo);
        }

        return optUsuario.get();
        }
    
}
