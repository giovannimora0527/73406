package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author 1031794_sofia_pedraza
 */

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene la lista de todos los usuarios registrados en la base de datos.
     *
     * @return Lista de usuarios.
     */
    @Override
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }
    
    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario a buscar.
     * @return Un {@code Optional<Usuario>} que contiene el usuario si se encuentra.
     * @throws BadRequestException Si el correo está vacío o si el usuario no se encuentra.
     */
    @Override
    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) throws BadRequestException {
        if (correo == null || correo.isEmpty()) {
            throw new BadRequestException("El correo no puede estar vacío.");
        }
        
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);

        if (!usuario.isPresent()) {
            throw new BadRequestException("No se encontró un usuario con el correo: " + correo);
        }

        return usuario;
    }
}