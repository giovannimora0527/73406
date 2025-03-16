/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author holma
 */
/**
 * Repositorio para la entidad Usuario.
 * Proporciona métodos para realizar operaciones en la base de datos.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param correo Correo del usuario a buscar.
     * @return Usuario encontrado, envuelto en un Optional.
     */
    Optional<Usuario> findByCorreo(String correo);
}