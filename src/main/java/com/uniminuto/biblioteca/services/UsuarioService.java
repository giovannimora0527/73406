package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;

public interface UsuarioService {

    /**
     * Obtiene la lista de todos los usuarios registrados en la biblioteca.
     *
     * @return Lista de usuarios.
     */
    List<Usuario> obtenerUsuarios();

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param correo Correo electrónico del usuario a buscar.
     * @return Un objeto Optional que contiene el usuario si se encuentra en la base de datos.
     * @throws BadRequestException Si el formato del correo es inválido o la consulta falla.
     */
    Optional<Usuario> obtenerUsuarioPorCorreo(String correo) throws BadRequestException;
}