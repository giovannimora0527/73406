package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import org.apache.coyote.BadRequestException;
import java.util.List;

public interface PrestamoService {

    List<Prestamo> listarTodos() throws BadRequestException;


    PrestamoRs guardarPrestamoNuevo(PrestamoRq prestamoRq) throws BadRequestException;

    PrestamoRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException;

}
