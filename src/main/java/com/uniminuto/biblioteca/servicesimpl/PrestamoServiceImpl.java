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
import java.util.stream.Collectors;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<PrestamoRs> obtenerListadoPrestamos() {
        return prestamoRepository.findAll()
                .stream()
                .map(this::mapPrestamoToPrestamoRs)
                .collect(Collectors.toList());
    }

    @Override
    public PrestamoRs obtenerPrestamoPorId(Integer idPrestamo) throws BadRequestException {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new BadRequestException("No se encuentra el préstamo con el ID " + idPrestamo));
        return mapPrestamoToPrestamoRs(prestamo);
    }

    @Override
    public PrestamoRs guardarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        // Validar usuario
        Usuario usuario = usuarioRepository.findById(prestamoRq.getIdUsuario())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        // Validar libro
        Libro libro = libroRepository.findById(prestamoRq.getIdLibro())
                .orElseThrow(() -> new BadRequestException("Libro no encontrado"));

        // Validar disponibilidad del libro
        long prestamosActivos = prestamoRepository.countByLibroIdLibroAndEstado(prestamoRq.getIdLibro(), EstadoPrestamo.PRESTADO);
        if (prestamosActivos >= libro.getExistencias()) {
            throw new BadRequestException("No hay ejemplares disponibles para este libro");
        }

        // Validar límite de préstamos por usuario
        if (prestamoRepository.countByUsuarioIdUsuarioAndEstado(prestamoRq.getIdUsuario(), EstadoPrestamo.PRESTADO) >= 3) {
            throw new BadRequestException("El usuario ya tiene el máximo de 3 préstamos activos");
        }

        // Validar fecha de devolución
        if (prestamoRq.getFechaDevolucion() == null || !prestamoRq.getFechaDevolucion().isAfter(LocalDate.now())) {
            throw new BadRequestException("La fecha de devolución debe ser posterior a hoy");
        }

        // Crear nuevo préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDateTime.now()); // Fecha actual automática
        prestamo.setFechaDevolucion(prestamoRq.getFechaDevolucion());
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

        // Guardar en base de datos
        prestamoRepository.save(prestamo);

        // Preparar respuesta
        PrestamoRs response = new PrestamoRs();
        response.setMessage("Préstamo registrado con éxito");
        return response;
    }

    @Override
    public PrestamoRs actualizarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        // Obtener préstamo existente
        Prestamo prestamo = prestamoRepository.findById(prestamoRq.getIdPrestamo())
                .orElseThrow(() -> new BadRequestException("Préstamo no encontrado"));

        // Actualizar usuario si se proporciona
        if (prestamoRq.getIdUsuario() != null) {
            Usuario usuario = usuarioRepository.findById(prestamoRq.getIdUsuario())
                    .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
            prestamo.setUsuario(usuario);
        }

        // Actualizar libro si se proporciona
        if (prestamoRq.getIdLibro() != null) {
            Libro libro = libroRepository.findById(prestamoRq.getIdLibro())
                    .orElseThrow(() -> new BadRequestException("Libro no encontrado"));
            prestamo.setLibro(libro);
        }

        // Actualizar fecha de devolución si se proporciona
        if (prestamoRq.getFechaDevolucion() != null) {
            prestamo.setFechaDevolucion(prestamoRq.getFechaDevolucion());
        }

        // Actualizar fecha de entrega y estado si se proporciona
        if (prestamoRq.getFechaEntrega() != null) {
            prestamo.setFechaEntrega(prestamoRq.getFechaEntrega());
            prestamo.setEstado(
                prestamoRq.getFechaEntrega().isAfter(prestamo.getFechaDevolucion()) 
                    ? EstadoPrestamo.VENCIDO 
                    : EstadoPrestamo.DEVUELTO
            );
        }

        // Guardar cambios
        prestamoRepository.save(prestamo);

        // Preparar respuesta
        PrestamoRs response = new PrestamoRs();
        response.setMessage("Préstamo actualizado con éxito");
        return response;
    }

    @Override
    public PrestamoRs registrarDevolucion(Integer idPrestamo) throws BadRequestException {
        // Obtener préstamo
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new BadRequestException("Préstamo no encontrado"));

        // Validar que no esté ya devuelto
        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new BadRequestException("El libro ya ha sido devuelto");
        }

        // Registrar devolución
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        prestamo.setFechaEntrega(LocalDate.now());

        // Guardar cambios
        prestamoRepository.save(prestamo);

        // Preparar respuesta
        PrestamoRs response = new PrestamoRs();
        response.setMessage("Devolución registrada con éxito");
        return response;
    }

    @Override
    public List<PrestamoRs> obtenerPrestamosPorUsuario(Integer idUsuario) throws BadRequestException {
        // Validar usuario
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new BadRequestException("Usuario no encontrado");
        }

        // Obtener préstamos del usuario
        return prestamoRepository.findByUsuarioIdUsuario(idUsuario)
                .stream()
                .map(this::mapPrestamoToPrestamoRs)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestamoRs> obtenerPrestamosPorLibro(Integer idLibro) throws BadRequestException {
        // Validar libro
        if (!libroRepository.existsById(idLibro)) {
            throw new BadRequestException("Libro no encontrado");
        }

        // Obtener préstamos del libro
        return prestamoRepository.findByLibroIdLibro(idLibro)
                .stream()
                .map(this::mapPrestamoToPrestamoRs)
                .collect(Collectors.toList());
    }

    @Override
    public List<PrestamoRs> obtenerPrestamosVencidos() {
        // Obtener préstamos vencidos (fecha devolución pasada y estado PRESTADO)
        return prestamoRepository.findByFechaDevolucionBeforeAndEstado(LocalDate.now(), EstadoPrestamo.PRESTADO)
                .stream()
                .map(this::mapPrestamoToPrestamoRs)
                .collect(Collectors.toList());
    }

    private PrestamoRs mapPrestamoToPrestamoRs(Prestamo prestamo) {
        PrestamoRs prestamoRs = new PrestamoRs();
        prestamoRs.setIdPrestamo(prestamo.getIdPrestamo());
        prestamoRs.setFechaPrestamo(prestamo.getFechaPrestamo().toLocalDate());
        prestamoRs.setFechaDevolucion(prestamo.getFechaDevolucion());
        prestamoRs.setFechaEntrega(prestamo.getFechaEntrega());
        prestamoRs.setEstado(prestamo.getEstado().name());

        // Mapear datos del usuario
        PrestamoRs.UsuarioSimple usuarioSimple = new PrestamoRs.UsuarioSimple();
        usuarioSimple.setIdUsuario(prestamo.getUsuario().getIdUsuario());
        usuarioSimple.setNombre(prestamo.getUsuario().getNombre());
        prestamoRs.setUsuario(usuarioSimple);

        // Mapear datos del libro
        PrestamoRs.LibroSimple libroSimple = new PrestamoRs.LibroSimple();
        libroSimple.setIdLibro(prestamo.getLibro().getIdLibro());
        libroSimple.setTitulo(prestamo.getLibro().getTitulo());
        prestamoRs.setLibro(libroSimple);

        return prestamoRs;
    }
}