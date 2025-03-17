package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {
    
    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> listarPrestamos() throws BadRequestException {
       return this.prestamoRepository.findAll();
    }

    @Override
    public Prestamo obtenerPrestamoId(Integer prestamoId) throws BadRequestException {        
        Optional<Prestamo> optPrestamo = this.prestamoRepository.findById(prestamoId);
        if (!optPrestamo.isPresent()) {
            throw new BadRequestException("No se encuentra el prestamo con el id = " 
                    + prestamoId);
        }
      return optPrestamo.get();
    }
}
