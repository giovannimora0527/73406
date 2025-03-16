/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Usuario.
 * Esta interfaz extiende JpaRepository para proporcionar operaciones CRUD
 * básicas para la entidad Usuario. Incluye un método personalizado para
 * buscar un usuario por su correo electrónico.
 * 
 * @author Santiago
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Encuentra un usuario por su correo electrónico.
     * 
     * @param correo El correo electrónico del usuario.
     * @return Un objeto Optional que contiene el usuario si se encuentra,
     *         o un Optional vacío si no se encuentra.
     */
    Optional<Usuario> findByCorreo(String correo);
}
