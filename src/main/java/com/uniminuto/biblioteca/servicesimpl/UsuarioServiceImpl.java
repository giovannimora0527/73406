package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        
        // Expresión regular para validar el formato de un correo electrónico
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(correo);

        if (!matcher.matches()) {
            throw new BadRequestException("El formato del correo es inválido: " + correo);
        }
        
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);

        if (!usuario.isPresent()) {
            throw new BadRequestException("No se encontró un usuario con el correo: " + correo);
        }

        return usuario;
    }
}