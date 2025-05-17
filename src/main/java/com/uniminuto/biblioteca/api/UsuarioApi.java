package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RequestMapping("/usuario")
public interface UsuarioApi {

    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Usuario>> listarUsuarios()
            throws BadRequestException;

    @RequestMapping(value = "/buscar-por-correo",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Usuario> buscarUsuarioPorEmail(
            @RequestParam String correo)
            throws BadRequestException;

    @RequestMapping(value = "/guardar-usuario",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<UsuarioRs> guardarUsuario(@RequestBody UsuarioRq usuario)
            throws BadRequestException;

    @RequestMapping(value = "/actualizar-usuario",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<UsuarioRs> actualizarUsuario(@RequestBody Usuario usuario)
            throws BadRequestException;

    // NUEVO: carga masiva de usuarios via archivo CSV
    @RequestMapping(value = "/carga-masiva",
            produces = {"application/json"},
            consumes = {"multipart/form-data"},
            method = RequestMethod.POST)
    ResponseEntity<UsuarioRs> cargaMasivaUsuarios(@RequestParam("file") MultipartFile file)
            throws BadRequestException;
}
