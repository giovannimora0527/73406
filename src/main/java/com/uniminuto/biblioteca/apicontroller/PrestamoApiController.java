/**
 * Implementación del controlador API para manejar las operaciones relacionadas con los préstamos.
 * 
 * <p>Esta clase es responsable de procesar las solicitudes HTTP para las operaciones de préstamo,
 * como listar los préstamos, guardar un nuevo préstamo y actualizar un préstamo existente.</p>
 * 
 * <p>Utiliza el servicio {@link PrestamoService} para realizar las acciones correspondientes en el backend.</p>
 * 
 * @author Santiago
 * @version 1.0
 */
package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.PrestamoApi;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador API que implementa las operaciones relacionadas con los préstamos.
 */
@RestController
public class PrestamoApiController implements PrestamoApi {

    @Autowired
    private PrestamoService prestamoService;

    /**
     * Obtiene la lista de todos los préstamos registrados en el sistema.
     * 
     * @return ResponseEntity con la lista de préstamos
     * @throws BadRequestException si ocurre un error durante la consulta
     */
    @Override
    public ResponseEntity<List<Prestamo>> listarPrestamos() throws BadRequestException {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    /**
     * Guarda un nuevo préstamo en el sistema.
     * 
     * @param prestamoRq datos del nuevo préstamo
     * @return ResponseEntity con el mensaje de éxito o error de la operación
     * @throws BadRequestException si el préstamo no puede ser guardado debido a un error
     */
    @Override
    public ResponseEntity<PrestamoRs> guardarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        PrestamoRs response = prestamoService.guardarPrestamoNuevo(prestamoRq);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza un préstamo existente en el sistema.
     * 
     * @param prestamo datos del préstamo a actualizar
     * @return ResponseEntity con el mensaje de éxito o error de la operación
     * @throws BadRequestException si el préstamo no puede ser actualizado debido a un error
     */
    @Override
    public ResponseEntity<PrestamoRs> actualizarPrestamo(Prestamo prestamo) throws BadRequestException {
        PrestamoRs response = prestamoService.actualizarPrestamo(prestamo);
        return ResponseEntity.ok(response);
    }

}
