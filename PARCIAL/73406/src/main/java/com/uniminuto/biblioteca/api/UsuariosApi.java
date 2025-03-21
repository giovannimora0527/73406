package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * API para la gestión de usuarios en la biblioteca.
 * Proporciona métodos para listar y buscar usuarios.
 *
 * @author pilol1111
 */
@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public interface UsuarioApi {

    /**
     * Método para listar los usuarios registrados en BD.
     *
     * @return Lista de usuarios.
     * @throws BadRequestException si ocurre un error en la consulta.
     */
    @GetMapping("/listar")
    ResponseEntity<List<Usuario>> listarUsuarios() throws BadRequestException;

    /**
     * Método para obtener un usuario por su ID.
     *
     * @param usuarioId ID del usuario.
     * @return Usuario encontrado.
     * @throws BadRequestException si el usuario no existe.
     */
    @GetMapping("/{usuarioId}")
    ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Integer usuarioId) throws BadRequestException;

    /**
     * Método para obtener un usuario por su correo electrónico.
     *
     * @param correo Correo del usuario.
     * @return Usuario encontrado.
     * @throws BadRequestException si el usuario no existe.
     */
    @GetMapping("/buscarPorCorreo")
    ResponseEntity<Usuario> obtenerUsuarioPorEmail(@RequestParam String correo) throws BadRequestException;
}
