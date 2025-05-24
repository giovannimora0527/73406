package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import java.util.List;
import java.util.Map;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lmora
 */
public interface UsuarioService {
    
    /**
     * Servicio para listar todos los usuarios del sistema.
     * @return Lista de usuarios registrados.
     * @throws BadRequestException Excepcion.
     */
    List<Usuario> listarTodo() throws BadRequestException;
    
    /**
     * Busca un usuario dado un email.
     * @param correo email a buscar.
     * @return Usuario.
     * @throws BadRequestException excepcion.
     */
    Usuario buscarPorCorreo(String correo) throws BadRequestException;
    
    
    /**
     * 
     * @param usuario
     * @return
     * @throws BadRequestException 
     */
    UsuarioRs guardarUsuarioNuevo(UsuarioRq usuario) throws BadRequestException;
    
    /**
     * 
     * @param usuario
     * @return
     * @throws BadRequestException 
     */
    UsuarioRs actualizarUsuario(Usuario usuario) throws BadRequestException;
    
    
    
    
    
    // Nuevo método para carga masiva
    Map<String, Object> cargarUsuariosDesdeCSV(MultipartFile archivo) throws BadRequestException;
    
    
    
}
