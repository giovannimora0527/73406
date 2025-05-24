package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import java.util.List;
import java.util.Map;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lmora
 */

public interface AutorService {
    
    
    List<Autor> obtenerListadoAutores();
   
    List<Nacionalidad> obtenerNacionalidadesDesdeAutores() throws BadRequestException;
    
    
    List<Autor> obtenerListadoAutoresPorNacionalidadId(String nacionalidadId)
            throws BadRequestException;
    
    List<Autor> listarAutoresPorNacionalidad(Integer nacionalidadId);
    
    
    Autor obtenerAutorPorId(Integer autorId) throws BadRequestException;
    
    /**
     * 
     * @param autor
     * @return
     * @throws BadRequestException 
     */
    AutorRs guardarAutorNuevo(AutorRq autor) throws BadRequestException;
    
    /**
     * 
     * @param autor
     * @return
     * @throws BadRequestException 
     */
    AutorRs actualizarAutor(AutorRq autor) throws BadRequestException;
    
    
    // Nuevo método para carga masiva
    Map<String, Object> cargarAutoresDesdeCSV(MultipartFile archivo) throws BadRequestException;
  
}
