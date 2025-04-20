/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author fx506
 */
@CrossOrigin(origins = "*")
@RequestMapping("/nacionalidad")
public interface NacionalidadApi {
    
    /**
     * Lista todos las nacionalidades registradas.
     * @return Lista de Nacionalidades.
     * @throws BadRequestException Excepción.
     */
    @RequestMapping(
            value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Nacionalidad>> listarNacionalidad() throws BadRequestException;
}
