
package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface PrestamoService {
    List<Prestamo> listarPrestamos() throws BadRequestException;
    
    Prestamo obtenerPrestamoId(Integer prestamoId) throws BadRequestException;
}
