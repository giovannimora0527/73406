
package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.repository.NacionalidadRepository;
import com.uniminuto.biblioteca.services.NacionalidadService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yeisonl
 */
@Service
public class NacionalidadServiceImpl implements NacionalidadService {

    @Autowired
    private NacionalidadRepository nacionalidadRepository;
            
    @Override
    public List<Nacionalidad> listarNacionalidad() throws BadRequestException {
        return nacionalidadRepository.findAll();
    }
    
} 