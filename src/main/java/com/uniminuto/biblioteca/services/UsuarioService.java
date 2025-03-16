/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz de servicio para la entidad Usuario.
 * Esta interfaz define los métodos que deben ser implementados por cualquier clase
 * que gestione las operaciones relacionadas con los usuarios en el sistema,
 * como la obtención de usuarios por correo y la lista completa de usuarios.
 * 
 * @author Santiago
 */
public interface UsuarioService {

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     * 
     * @return Una lista de objetos Usuario.
     * @throws BadRequestException Si ocurre un error durante la operación.
     */
    List<Usuario> listarUsuarios() throws BadRequestException;

    /**
     * Obtiene un usuario basado en su correo electrónico.
     * 
     * @param UsuarioCorreo El correo electrónico del usuario.
     * @return El usuario correspondiente al correo proporcionado.
     * @throws BadRequestException Si no se encuentra un usuario con ese correo electrónico.
     */
    Usuario obtenerUsuarioCorreo(String UsuarioCorreo) throws BadRequestException;
}
