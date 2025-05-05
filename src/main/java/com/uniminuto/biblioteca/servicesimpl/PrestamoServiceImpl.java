package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Prestamo.EstadoPrestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    // Método para convertir entidad Prestamo a DTO PrestamoRs
    private PrestamoRs mapPrestamoToPrestamoRs(Prestamo prestamo) {
        PrestamoRs prestamoRs = new PrestamoRs();
        prestamoRs.setIdPrestamo(prestamo.getIdPrestamo());
        prestamoRs.setFechaPrestamo(prestamo.getFechaPrestamo().toLocalDate());
        prestamoRs.setFechaDevolucion(prestamo.getFechaDevolucion());
        prestamoRs.setFechaEntrega(prestamo.getFechaEntrega());
        prestamoRs.setEstado(prestamo.getEstado().name());

        PrestamoRs.UsuarioSimple usuarioSimple = new PrestamoRs.UsuarioSimple();
        usuarioSimple.setIdUsuario(prestamo.getUsuario().getIdUsuario());
        usuarioSimple.setNombre(prestamo.getUsuario().getNombre());
        prestamoRs.setUsuario(usuarioSimple);

        PrestamoRs.LibroSimple libroSimple = new PrestamoRs.LibroSimple();
        libroSimple.setIdLibro(prestamo.getLibro().getIdLibro());
        libroSimple.setTitulo(prestamo.getLibro().getTitulo());
        prestamoRs.setLibro(libroSimple);

        return prestamoRs;
    }

    @Override
    public List<PrestamoRs> obtenerListadoPrestamos() {
        return prestamoRepository.findAll()
                .stream()
                .map(this::mapPrestamoToPrestamoRs)
                .toList();
    }

    @Override
    public PrestamoRs obtenerPrestamoPorId(Integer idPrestamo) throws BadRequestException {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new BadRequestException("No se encuentra el préstamo con el ID " + idPrestamo));
        return mapPrestamoToPrestamoRs(prestamo);
    }

    @Override
    public PrestamoRs guardarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        Usuario usuario = usuarioRepository.findById(prestamoRq.getIdUsuario())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(prestamoRq.getIdLibro())
                .orElseThrow(() -> new BadRequestException("Libro no encontrado"));

        long prestamosActivos = prestamoRepository.countByLibroIdLibroAndEstado(prestamoRq.getIdLibro(), EstadoPrestamo.PRESTADO);
        if (prestamosActivos >= libro.getExistencias()) {
            throw new BadRequestException("No hay ejemplares disponibles para este libro");
        }

        if (prestamoRepository.countByUsuarioIdUsuarioAndEstado(prestamoRq.getIdUsuario(), EstadoPrestamo.PRESTADO) >= 3) {
            throw new BadRequestException("El usuario ya tiene el máximo de 3 préstamos activos");
        }

        // Validación de fecha de devolución: mínimo 24 horas después de ahora
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaDevolucion = prestamoRq.getFechaDevolucion().atStartOfDay(); // Convierte LocalDate a LocalDateTime a las 00:00 horas

// Verifica que la fecha de devolución sea al menos 24 horas después de la fecha actual
        if (prestamoRq.getFechaDevolucion() == null || !fechaDevolucion.isAfter(ahora.plusHours(24))) {
            throw new BadRequestException("La fecha de devolución debe ser al menos 24 horas después de la fecha actual");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(ahora);
        prestamo.setFechaDevolucion(prestamoRq.getFechaDevolucion());
        prestamo.setEstado(EstadoPrestamo.PRESTADO); // Fijo y no modificable al crear

        prestamoRepository.save(prestamo);

        PrestamoRs response = new PrestamoRs();
        response.setMessage("Préstamo registrado con éxito");
        return response;
    }

    @Override
    public PrestamoRs actualizarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        Prestamo prestamo = prestamoRepository.findById(prestamoRq.getIdPrestamo())
                .orElseThrow(() -> new BadRequestException("Préstamo no encontrado"));

        if (prestamoRq.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(prestamoRq.getIdUsuario())
                    .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
            prestamo.setUsuario(usuario);
        }

        if (prestamoRq.getIdLibro() != null) {
            Libro libro = libroRepository.findById(prestamoRq.getIdLibro())
                    .orElseThrow(() -> new BadRequestException("Libro no encontrado"));
            prestamo.setLibro(libro);
        }

        if (prestamoRq.getFechaDevolucion() != null) {
            prestamo.setFechaDevolucion(prestamoRq.getFechaDevolucion());
        }

        if (prestamoRq.getFechaEntrega() != null) {
            prestamo.setFechaEntrega(prestamoRq.getFechaEntrega());

            // Verificar si se entregó después de la fecha de devolución
            if (prestamoRq.getFechaEntrega().isAfter(prestamo.getFechaDevolucion())) {
                prestamo.setEstado(EstadoPrestamo.VENCIDO);
            } else {
                prestamo.setEstado(EstadoPrestamo.DEVUELTO); // Opcional si quieres marcarlo como devuelto en caso contrario
            }
        }

        prestamoRepository.save(prestamo);

        PrestamoRs response = new PrestamoRs();
        response.setMessage("Préstamo actualizado con éxito");
        return response;
    }

    @Override
    public PrestamoRs registrarDevolucion(Integer idPrestamo) throws BadRequestException {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new BadRequestException("Préstamo no encontrado"));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new BadRequestException("El libro ya ha sido devuelto");
        }

        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        prestamo.setFechaEntrega(LocalDate.now());

        prestamoRepository.save(prestamo);

        PrestamoRs response = new PrestamoRs();
        response.setMessage("Devolución registrada con éxito");
        return response;
    }

    @Override
    public List<PrestamoRs> obtenerPrestamosPorUsuario(Integer idUsuario) throws BadRequestException {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new BadRequestException("Usuario no encontrado");
        }
        return prestamoRepository.findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(this::mapPrestamoToPrestamoRs)
                .toList();
    }

    @Override
    public List<PrestamoRs> obtenerPrestamosPorLibro(Integer idLibro) throws BadRequestException {
        if (!libroRepository.existsById(idLibro)) {
            throw new BadRequestException("Libro no encontrado");
        }
        return prestamoRepository.findByLibroIdLibro(idLibro)
                .stream()
                .map(this::mapPrestamoToPrestamoRs)
                .toList();
    }

    @Override
    public List<PrestamoRs> obtenerPrestamosVencidos() {
        return prestamoRepository.findByFechaDevolucionBeforeAndEstado(LocalDate.now(), EstadoPrestamo.PRESTADO)
                .stream()
                .map(this::mapPrestamoToPrestamoRs)
                .toList();
    }
}
