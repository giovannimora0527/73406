/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.NacionalidadApi;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.services.NacionalidadService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fx506
 */
@RestController
public class NacionalidadApiController implements NacionalidadApi {
    
    @Autowired
    private NacionalidadService nacionalidadService;

    @Override
    public ResponseEntity<List<Nacionalidad>> listarNacionalidad() throws BadRequestException {
               return ResponseEntity.ok(this.nacionalidadService.listarNacionalidad());
    }
    
    
}
