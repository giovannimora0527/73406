package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 *
 * @author pilol1111
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>  {


    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    Usuario findByCorreo(String correo);

}