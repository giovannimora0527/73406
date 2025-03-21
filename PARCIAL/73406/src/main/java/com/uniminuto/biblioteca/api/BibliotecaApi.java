package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.model.TestRs;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * API de la biblioteca.
 * Proporciona un método de prueba para verificar el estado del servicio.
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/app")
public interface BibliotecaApi {

    /**
     * Método de prueba del servicio.
     *
     * @return Respuesta de prueba con estado OK.
     * @throws BadRequestException si ocurre un error.
     */
    @GetMapping("/test")
    ResponseEntity<TestRs> testService() throws BadRequestException;
}


