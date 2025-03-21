package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 *
 * @author fx506
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);
}