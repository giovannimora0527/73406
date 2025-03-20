package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.AutorApi;
import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST que maneja las solicitudes relacionadas con autores.
 * Implementa la interfaz {@link AutorApi}.
 * 
 * Proporciona endpoints para obtener la lista de autores,
 * buscar autores por nacionalidad y obtener un autor por su ID.
 * 
 * @author lmora
 */
@RestController
public class AutorApiController implements AutorApi {
    /**
     * Servicio para la gestión de autores.
     */
    @Autowired
    private AutorService autorService;
    
    
    /**
     * Lista todos los autores registrados en la base de datos.
     *
     * @return ResponseEntity con la lista de autores en formato JSON.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<List<Autor>> listarAutores() throws BadRequestException {
       return ResponseEntity.ok(this.autorService.obtenerListadoAutores());
    }
    
    /**
     * Lista los autores filtrados por nacionalidad.
     *
     * @param nacionalidad Nacionalidad de los autores a buscar.
     * @return ResponseEntity con la lista de autores encontrados.
     * @throws BadRequestException si ocurre un error en la solicitud.
     */
    @Override
    public ResponseEntity<List<Autor>> listarAutoresByNacionalidad(String nacionalidad) 
            throws BadRequestException {
       return ResponseEntity.ok(this.autorService
               .obtenerListadoAutoresPorNacionalidad(nacionalidad));
    }
    
    /**
     * Obtiene un autor específico a partir de su ID.
     *
     * @param idAutor ID del autor a buscar.
     * @return ResponseEntity con la información del autor encontrado.
     * @throws BadRequestException si el autor no existe o hay un error en la solicitud.
     */
    @Override
    public ResponseEntity<Autor> listarAutorPorId(Long idAutor) throws BadRequestException {
       return ResponseEntity.ok(this.autorService.obtenerAutorPorId(idAutor));
    }  
}
