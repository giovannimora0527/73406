package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/Usuario")
public interface UsuarioApi {

    /**
     * Metodo para listar los Usuarios registrados en bd.
     *
     * @return Lista de Usuarios.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException;
    
     /**
     * Metodo para listar los usuarios registrados en bd.
     *
     * @param UsuarioId Id del Usuario.
     * @return Lista de autores.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-Usuario-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> obtenerUsuarioPorId (@RequestParam Integer UsuarioId)
            throws BadRequestException;
    /**
 * Método para obtener un usuario por su email.
 * 
 * @param correo
 * @return Usuario encontrado.
 * @throws BadRequestException Excepción si no se encuentra el usuario.
 */
@RequestMapping(value = "/obtener-usuario-email",
    produces = {"application/json"},
    consumes = {"application/json"},
    method = RequestMethod.GET)
    ResponseEntity<Usuario> obtenerUsuarioEmail (@RequestParam String correo)
            throws BadRequestException;

}
