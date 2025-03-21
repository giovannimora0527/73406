package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * @return Todos los usuarios registrados
     * @throws BadRequestException
     */
    @Override
    public List<Usuario> listarUsuarios() throws BadRequestException {
        return this.usuarioRepository.findAll();
    }

    /**
     * @param usuarioId
     * @return usuario por id
     * @throws BadRequestException
     */
    @Override
    public Usuario obtenerUsuarioId(Integer usuarioId) throws BadRequestException {
        Optional<Usuario> optUsuario = this.usuarioRepository.findById(usuarioId);
        if (!optUsuario.isPresent()) {
            throw new BadRequestException("No se encuentra el Usuario con el id = "
                    + usuarioId);
        }
        return optUsuario.get();
    }

    /**
     * @param correo
     * @return Usuario por correo
     * @throws BadRequestException
     */
    @Override
    public Usuario obtenerUsuarioPorEmail(String correo) throws BadRequestException {
        // Cadena de caracteres para verificar estructura de email
        String regexPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Validación de estructura del correo
        if (!Pattern.matches(regexPattern, correo)) {
            throw new BadRequestException("El formato del correo electrónico no es válido");
        }

        // Buscar usuario por correo
        Usuario usuario = this.usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            throw new BadRequestException("No se encuentra el Usuario con el correo = " + correo);
        }
        return usuario;
    }
}


