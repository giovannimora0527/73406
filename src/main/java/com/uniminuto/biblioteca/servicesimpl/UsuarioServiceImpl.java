/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author holma
 */

/**
 * Implementación de la interfaz UsuarioService para la gestión de usuarios.
 * Proporciona métodos para listar, guardar, eliminar y buscar usuarios en la base de datos.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    
    
    
    /**
     * Obtiene la lista de todos los usuarios registrados en la biblioteca.
     *
     * @return Lista de usuarios existentes en la base de datos.
     */
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

   
    
    /**
     * Obtiene un usuario por su correo electrónico con validación de formato.
     *
     * @param correo Correo electrónico del usuario a buscar.
     * @return Usuario encontrado, envuelto en un Optional.
     * @throws IllegalArgumentException si el formato del correo es inválido.
     */
    @Override
    public Optional<Usuario> obtenerUsuarioPorCorreo(String correo) {
        if (!validarCorreo(correo)) {
            throw new IllegalArgumentException("Formato de correo inválido: " + correo);
        }
        return usuarioRepository.findByCorreo(correo);
    }


    /**
     * Valida el formato de un correo electrónico utilizando una expresión regular.
     *
     * @param correo Correo a validar.
     * @return true si el correo tiene un formato válido, false en caso contrario.
     */
    private boolean validarCorreo(String correo) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();
    }
}