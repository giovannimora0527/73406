package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    boolean existsByCorreo(String correo);
}
