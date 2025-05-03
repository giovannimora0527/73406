package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que implementa las operaciones para gestionar préstamos de libros.
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> listarTodos() throws BadRequestException {
        return prestamoRepository.findAll();
    }

    @Override
    public PrestamoRs guardarPrestamoNuevo(PrestamoRq prestamoRq) throws BadRequestException {
        Usuario usuario = usuarioRepository.findById(prestamoRq.getIdUsuario())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(prestamoRq.getIdLibro())
                .orElseThrow(() -> new BadRequestException("Libro no encontrado"));

        // Validación de fechas: usa LocalDateTime directamente
        if (prestamoRq.getFechaDevolucion() != null
                && !prestamoRq.getFechaDevolucion().isAfter(prestamoRq.getFechaPrestamo())) {
            throw new BadRequestException("La fecha de devolución debe ser posterior a la fecha de préstamo");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);

        // fechaPrestamo es LocalDateTime en la entidad: asignamos directamente
        prestamo.setFechaPrestamo(prestamoRq.getFechaPrestamo());

        // fechaDevolucion es LocalDate en la entidad: convertimos
        if (prestamoRq.getFechaDevolucion() != null) {
            prestamo.setFechaDevolucion(prestamoRq.getFechaDevolucion().toLocalDate());
        }

        prestamo.setEstado(Prestamo.EstadoPrestamo.PRESTADO);
        prestamoRepository.save(prestamo);

        PrestamoRs response = new PrestamoRs();
        response.setMessage("Préstamo guardado correctamente");
        return response;
    }

    @Override
    public PrestamoRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException {
        Optional<Prestamo> prestamoExistenteOpt = prestamoRepository.findById(prestamo.getIdPrestamo());
        if (!prestamoExistenteOpt.isPresent()) {
            throw new BadRequestException("Préstamo no encontrado");
        }

        Prestamo prestamoExistente = prestamoExistenteOpt.get();

        if (prestamoExistente.getFechaDevolucion() == null) {
            throw new BadRequestException("El préstamo no tiene una fecha de devolución registrada");
        }

        if (prestamo.getFechaEntrega() != null) {
            // fechaEntrega en la entidad es LocalDate
            LocalDate entrega = prestamo.getFechaEntrega();

            // Comparamos con fechaPrestamo (LocalDateTime) extrayendo la fecha
            if (entrega.isBefore(prestamoExistente.getFechaPrestamo().toLocalDate())) {
                throw new BadRequestException("La fecha de entrega no puede ser antes de la fecha de préstamo");
            }

            prestamoExistente.setFechaEntrega(entrega);

            // Comparamos con fechaDevolucion (ambas LocalDate)
            if (entrega.isAfter(prestamoExistente.getFechaDevolucion())) {
                prestamoExistente.setEstado(Prestamo.EstadoPrestamo.VENCIDO);
            } else {
                prestamoExistente.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
            }
        }

        prestamoRepository.save(prestamoExistente);

        PrestamoRs response = new PrestamoRs();
        response.setMessage("Préstamo actualizado correctamente");
        return response;
    }
}
