package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    /**
     * Patron para validar email.
     */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    /**
     * Regex para validacion de email.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Repositorio de usuario.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodo() throws BadRequestException {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorCorreo(String correo) throws BadRequestException {
        if (correo == null || correo.isBlank()) {
            throw new BadRequestException("El correo: " + correo + ", no cumple "
                    + "la validación para ser un correo valido.");
        }

        boolean isValidoEmail = this.validarCorreo(correo);
        if (!isValidoEmail) {
            throw new BadRequestException("El correo no es valido.");
        }

        Optional<Usuario> optUsuario = this.usuarioRepository
                .findByCorreo(correo);
        if (!optUsuario.isPresent()) {
            throw new BadRequestException("No hay registros de usuarios "
                    + "registrados con el correo ingresado.");
        }
        return optUsuario.get();
    }

    /**
     *
     * @param correo
     * @return
     */
    public boolean validarCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(correo).matches();
    }

    @Override
    public UsuarioRs guardarUsuarioNuevo(UsuarioRq usuario) throws BadRequestException {
        Optional<Usuario> optUser = this.usuarioRepository
                .findByNombre(usuario.getNombreCompleto());
        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya existe con el nombre "
                    + usuario.getNombreCompleto()
                    + ", Verifique e intente de nuevo.");
        }

        optUser = this.usuarioRepository
                .findByCorreo(usuario.getCorreo());

        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya existe con el correo "
                    + usuario.getCorreo()
                    + ", Verifique e intente de nuevo.");
        }     
        this.usuarioRepository.save(this.convertirUsuarioRqToUsuario(usuario));
        UsuarioRs rta = new UsuarioRs();
        rta.setMessage("Se ha guardado el usuario con exito.");
        return rta;
    }
    
    
    private Usuario convertirUsuarioRqToUsuario(UsuarioRq usuario) {
        Usuario user = new Usuario();
        user.setNombre(usuario.getNombreCompleto());
        user.setActivo(true);
        user.setFechaRegistro(LocalDateTime.now());
        user.setCorreo(usuario.getCorreo());
        user.setTelefono(usuario.getTelefono());
        return user;
    }

    @Override
    public UsuarioRs actualizarUsuario(UsuarioRq usuario) throws BadRequestException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
