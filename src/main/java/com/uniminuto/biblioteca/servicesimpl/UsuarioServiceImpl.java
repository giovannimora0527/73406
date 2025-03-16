package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de usuarios.
 * Esta clase contiene la lógica de negocio relacionada con los usuarios,
 * incluyendo la obtención de usuarios por correo electrónico y la validación
 * del formato del correo electrónico.
 * 
 * @author santiago
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     * 
     * @return Una lista de objetos Usuario.
     * @throws BadRequestException Si ocurre un error durante la operación.
     */
    @Override
    public List<Usuario> listarUsuarios() throws BadRequestException {
        return this.usuarioRepository.findAll();
    }

    /** Expresión regular para validar el formato del correo electrónico */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    /**
     * Obtiene un usuario por su correo electrónico.
     * 
     * @param usuarioCorreo El correo electrónico del usuario.
     * @return El usuario correspondiente al correo electrónico proporcionado.
     * @throws BadRequestException Si el formato del correo es inválido o si no se encuentra el usuario.
     */
    @Override
    public Usuario obtenerUsuarioCorreo(String usuarioCorreo) throws BadRequestException {
        // Validar el formato del correo
        if (!isValidEmail(usuarioCorreo)) {
            throw new BadRequestException("El correo electrónico no tiene un formato válido: " + usuarioCorreo);
        }

        // Buscar el usuario en la base de datos
        Optional<Usuario> optUsuario = this.usuarioRepository.findByCorreo(usuarioCorreo);
        if (!optUsuario.isPresent()) {
            throw new BadRequestException("No se encuentra el usuario con el correo = " + usuarioCorreo);
        }

        return optUsuario.get();
    }

    /**
     * Valida el formato de un correo electrónico utilizando una expresión regular.
     * 
     * @param email El correo electrónico a validar.
     * @return true si el correo tiene un formato válido, false si no.
     */
    private boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
}
