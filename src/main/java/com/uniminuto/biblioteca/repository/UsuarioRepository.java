package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link Usuario}.
 * Proporciona métodos para interactuar con la base de datos.
 * 
 * @author 1031794_sofia_pedraza
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Busca una lista de usuarios por su nombre.
     * 
     * @param nombre Nombre del usuario a buscar.
     * @return Lista de usuarios que coinciden con el nombre proporcionado.
     */
    List<Usuario> findByNombre(String nombre);
    
    /**
     * Busca un usuario por su correo electrónico.
     * 
     * @param correo Correo del usuario a buscar.
     * @return Un {@link Optional} que contiene el usuario si se encuentra, o vacío si no.
     */
    Optional<Usuario> findByCorreo(String correo);
}