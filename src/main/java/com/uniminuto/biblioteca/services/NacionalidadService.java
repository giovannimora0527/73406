/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author fx506
 */
public interface NacionalidadService {
        List<Nacionalidad> listarNacionalidad() throws BadRequestException;
}
