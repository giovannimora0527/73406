/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author holma
 */
/**
 * Servicio para la gestión de usuarios.
 */
public interface UsuarioService {
    /**
     * Obtiene la lista de todos los usuarios registrados en la biblioteca.
     * @return Lista de usuarios.
     */
    List<Usuario> listarUsuarios();


    /**
     * Obtiene un usuario por su correo electrónico.
     * Se valida el formato del correo antes de realizar la búsqueda.
     * 
     * @param correo Correo electrónico a buscar.
     * @return Usuario encontrado si existe.
     * @throws IllegalArgumentException Si el correo no tiene un formato válido.
     */
    Optional<Usuario> obtenerUsuarioPorCorreo(String correo);

}

