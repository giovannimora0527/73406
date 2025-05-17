package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

public interface UsuarioService {

    List<Usuario> listarTodo() throws BadRequestException;

    Usuario buscarPorCorreo(String correo) throws BadRequestException;

    UsuarioRs guardarUsuarioNuevo(UsuarioRq usuario) throws BadRequestException;

    UsuarioRs actualizarUsuario(Usuario usuario) throws BadRequestException;

    // NUEVO: carga masiva CSV
    UsuarioRs cargaMasivaUsuarios(MultipartFile file) throws BadRequestException;
}
