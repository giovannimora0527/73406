package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.UsuarioRq;
import com.uniminuto.biblioteca.model.UsuarioRs;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    /**
     * Patron para validar email.
     */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    /**
     * Regex para validacion de email.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    /**
     * Repositorio de usuario.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodo() throws BadRequestException {
        return this.usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorCorreo(String correo) throws BadRequestException {
        if (correo == null || correo.isBlank()) {
            throw new BadRequestException("El correo: " + correo + ", no cumple "
                    + "la validación para ser un correo valido.");
        }

        boolean isValidoEmail = this.validarCorreo(correo);
        if (!isValidoEmail) {
            throw new BadRequestException("El correo no es valido.");
        }

        Optional<Usuario> optUsuario = this.usuarioRepository
                .findByCorreo(correo);
        if (!optUsuario.isPresent()) {
            throw new BadRequestException("No hay registros de usuarios "
                    + "registrados con el correo ingresado.");
        }
        return optUsuario.get();
    }

    /**
     *
     * @param correo
     * @return
     */
    public boolean validarCorreo(String correo) {
        if (correo == null || correo.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(correo).matches();
    }
    
    

    @Override
    public UsuarioRs guardarUsuarioNuevo(UsuarioRq usuario) throws BadRequestException {
        Optional<Usuario> optUser = this.usuarioRepository
                .findByNombre(usuario.getNombre());
        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya existe con el nombre "
                    + usuario.getNombre()
                    + ", Verifique e intente de nuevo.");
        }

        optUser = this.usuarioRepository
                .findByCorreo(usuario.getCorreo());

        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya existe con el correo "
                    + usuario.getCorreo()
                    + ", Verifique e intente de nuevo.");
        }
        this.usuarioRepository.save(this.convertirUsuarioRqToUsuario(usuario));
        UsuarioRs rta = new UsuarioRs();
        rta.setMessage("Se ha guardado el usuario con exito.");
        return rta;
    }

    private Usuario convertirUsuarioRqToUsuario(UsuarioRq usuario) {
        Usuario user = new Usuario();
        user.setNombre(usuario.getNombre());
        user.setActivo(true);
        user.setFechaRegistro(LocalDateTime.now());
        user.setCorreo(usuario.getCorreo());
        user.setTelefono(usuario.getTelefono());
        return user;
    }

    @Override
    public UsuarioRs actualizarUsuario(Usuario usuario) throws BadRequestException {        
        Optional<Usuario> optUser = this.usuarioRepository.findById(usuario.getIdUsuario());
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario.");
        }
        UsuarioRs rta = new UsuarioRs();
        rta.setMessage("El usuario se actualizó correctamente.");
        Usuario userActual = optUser.get();
        if (!cambioObjeto(userActual, usuario)) {
            return rta;
        }

        if (!usuario.getNombre().equals(userActual.getNombre())) {
           if (this.usuarioRepository.existsByNombre(usuario.getNombre())) {
               throw new BadRequestException("El nombre del usuario: " + usuario.getNombre() 
                       + ", existe en la bd. Verifique e intente de nuevo.");
           }
        }
        if (!usuario.getCorreo().equals(userActual.getCorreo())) {
            if (this.usuarioRepository.existsByCorreo(usuario.getCorreo())) {
               throw new BadRequestException("El correo del usuario: " + usuario.getCorreo() 
                       + ", existe en la bd. Verifique e intente de nuevo.");
           }
        }
        userActual.setNombre(usuario.getNombre());
        userActual.setCorreo(usuario.getCorreo());
        userActual.setTelefono(usuario.getTelefono());
        userActual.setActivo(usuario.getActivo());
        this.usuarioRepository.save(userActual);
        return rta;
    }

    private boolean cambioObjeto(Usuario userActual, Usuario userFront) {
        if (!userActual.getNombre().equals(userFront.getNombre())
                || !userActual.getCorreo().equals(userFront.getCorreo())
                || !userActual.getTelefono().equals(userFront.getTelefono())
                || !userActual.getActivo().equals(userFront.getActivo())) {
            return true;
        }
        return false;
    }
    
    
    // CARGA DE SUSUARIOS DESDE CSV
   @Override
    public Map<String, Object> cargarUsuariosDesdeCSV(MultipartFile archivo) throws BadRequestException {
        if (archivo == null || archivo.isEmpty()) {
            throw new BadRequestException("El archivo es obligatorio.");
        }

        if (!archivo.getOriginalFilename().endsWith(".csv")) {
            throw new BadRequestException("Solo se permiten archivos CSV.");
        }

        List<Usuario> usuariosGuardados = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        int numeroFila = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {
            String linea;
            String[] cabeceras = null;

            // Leer cabeceras
            if ((linea = br.readLine()) != null) {
                cabeceras = linea.split(",");
                // Convertir cabeceras a minúsculas y eliminar espacios
                for (int i = 0; i < cabeceras.length; i++) {
                    cabeceras[i] = cabeceras[i].trim().toLowerCase();
                }

                // Verificar que existan las columnas requeridas
                boolean tieneNombre = false;
                boolean tieneCorreo = false;
                boolean tieneTelefono = false;

                for (String cabecera : cabeceras) {
                    if ("nombre".equals(cabecera)) tieneNombre = true;
                    if ("correo".equals(cabecera)) tieneCorreo = true;
                    if ("telefono".equals(cabecera)) tieneTelefono = true;
                }

                if (!tieneNombre || !tieneCorreo || !tieneTelefono) {
                    throw new BadRequestException("El archivo CSV debe contener las columnas: nombre, correo, telefono.");
                }
            } else {
                throw new BadRequestException("El archivo CSV está vacío.");
            }

            // Expresiones regulares para validación
            Pattern nombrePattern = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$"); // Solo letras y espacios
            Pattern telefonoPattern = Pattern.compile("^\\d{1,15}$"); // Solo números, max 15 dígitos

            // Procesar filas de datos
            while ((linea = br.readLine()) != null) {
                numeroFila++;

                try {
                    String[] valores = linea.split(",");

                    // Validar que tenga suficientes columnas
                    if (valores.length < cabeceras.length) {
                        errores.add("Fila " + numeroFila + ": No contiene todas las columnas necesarias.");
                        continue;
                    }

                    // Crear mapa de columnas y valores
                    Map<String, String> datosFila = new HashMap<>();
                    for (int i = 0; i < cabeceras.length; i++) {
                        datosFila.put(cabeceras[i], valores[i].trim());
                    }

                    // Obtener valores
                    String nombre = datosFila.get("nombre");
                    String correo = datosFila.get("correo");
                    String telefono = datosFila.get("telefono");

                    // Validación del nombre
                    if (nombre == null || nombre.isEmpty()) {
                        errores.add("Fila " + numeroFila + ": El nombre es obligatorio.");
                        continue;
                    }

                    if (!nombrePattern.matcher(nombre).matches()) {
                        errores.add("Fila " + numeroFila + ": El nombre solo debe contener letras: " + nombre);
                        continue;
                    }

                    // Validación del correo
                    if (correo == null || correo.isEmpty()) {
                        errores.add("Fila " + numeroFila + ": El correo es obligatorio.");
                        continue;
                    }

                    if (!this.validarCorreo(correo)) {
                        errores.add("Fila " + numeroFila + ": El formato del correo no es válido: " + correo);
                        continue;
                    }

                    // Validación del teléfono
                    if (telefono == null || telefono.isEmpty()) {
                        errores.add("Fila " + numeroFila + ": El teléfono es obligatorio.");
                        continue;
                    }

                    if (!telefonoPattern.matcher(telefono).matches()) {
                        errores.add("Fila " + numeroFila + ": El teléfono debe contener solo números y tener máximo 15 dígitos: " + telefono);
                        continue;
                    }

                    // Verificar si ya existe
                    Optional<Usuario> usuarioExistenteCorreo = this.usuarioRepository.findByCorreo(correo);
                    if (usuarioExistenteCorreo.isPresent()) {
                        errores.add("Fila " + numeroFila + ": Ya existe un usuario con el correo: " + correo);
                        continue;
                    }

                    Optional<Usuario> usuarioExistenteNombre = this.usuarioRepository.findByNombre(nombre);
                    if (usuarioExistenteNombre.isPresent()) {
                        errores.add("Fila " + numeroFila + ": Ya existe un usuario con el nombre: " + nombre);
                        continue;
                    }

                    // Crear usuario
                    Usuario usuario = new Usuario();
                    usuario.setNombre(nombre);
                    usuario.setCorreo(correo);
                    usuario.setTelefono(telefono);
                    usuario.setFechaRegistro(LocalDateTime.now());
                    usuario.setActivo(true);

                    // Guardar usuario
                    usuariosGuardados.add(this.usuarioRepository.save(usuario));

                } catch (Exception e) {
                    errores.add("Fila " + numeroFila + ": Error procesando la fila - " + e.getMessage());
                }
            }

            Map<String, Object> resultado = new HashMap<>();
            resultado.put("usuariosGuardados", usuariosGuardados.size());
            resultado.put("errores", errores);

            if (usuariosGuardados.isEmpty() && !errores.isEmpty()) {
                throw new BadRequestException("No se pudo procesar ningún usuario. Verifique los errores.");
            }

            return resultado;

        } catch (IOException e) {
            throw new BadRequestException("Error al leer el archivo: " + e.getMessage());
        }
    }
}
