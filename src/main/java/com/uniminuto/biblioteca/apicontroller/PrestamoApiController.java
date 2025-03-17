package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.services.PrestamoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author lmora
 */
@RestController
public class PrestamoApiController implements PrestamoApi {
    
    @Autowired
    private PrestamoService prestamoService;
    
    @Override
    public ResponseEntity<List<Prestamo>> ListarPrestamos()
            throws BadRequestException {
        return ResponseEntity.ok (this.prestamoService.listarPrestamos());
    }

    @Override
    public ResponseEntity<Prestamo> obtenerPrestamosPorId(Integer prestamoId) throws BadRequestException {
      return ResponseEntity.ok(this.prestamoService.obtenerPrestamoId(prestamoId));
    }
}
