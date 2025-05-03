package com.uniminuto.biblioteca.servicesimpl;

<<<<<<< HEAD
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Prestamo.EstadoPrestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
=======
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
>>>>>>> 708e27d9b1709601d41210363af62176929a3745
import com.uniminuto.biblioteca.services.PrestamoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
=======
/**
 * Servicio que implementa las operaciones para gestionar préstamos de libros.
 */
>>>>>>> 708e27d9b1709601d41210363af62176929a3745
@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
<<<<<<< HEAD
    private PrestamoRepository prestamoRepository;

    @Autowired
=======
>>>>>>> 708e27d9b1709601d41210363af62176929a3745
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

<<<<<<< HEAD
    // 🔵 Método privado para mapear de entidad Prestamo a DTO PrestamoRs
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
    public PrestamoRs registrarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
=======
    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> listarTodos() throws BadRequestException {
        return prestamoRepository.findAll();
    }

    @Override
    public PrestamoRs guardarPrestamoNuevo(PrestamoRq prestamoRq) throws BadRequestException {
>>>>>>> 708e27d9b1709601d41210363af62176929a3745
        Usuario usuario = usuarioRepository.findById(prestamoRq.getIdUsuario())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(prestamoRq.getIdLibro())
                .orElseThrow(() -> new BadRequestException("Libro no encontrado"));

<<<<<<< HEAD
        if (prestamoRepository.existsByLibroIdLibroAndEstado(prestamoRq.getIdLibro(), EstadoPrestamo.PRESTADO)) {
            throw new BadRequestException("El libro ya está prestado");
        }

        if (prestamoRepository.countByUsuarioIdUsuarioAndEstado(prestamoRq.getIdUsuario(), EstadoPrestamo.PRESTADO) >= 3) {
            throw new BadRequestException("El usuario ya tiene el máximo de 3 préstamos activos");
=======
        // Validación de fechas: usa LocalDateTime directamente
        if (prestamoRq.getFechaDevolucion() != null
                && !prestamoRq.getFechaDevolucion().isAfter(prestamoRq.getFechaPrestamo())) {
            throw new BadRequestException("La fecha de devolución debe ser posterior a la fecha de préstamo");
>>>>>>> 708e27d9b1709601d41210363af62176929a3745
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
<<<<<<< HEAD
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setFechaDevolucion(prestamoRq.getFechaDevolucion());
        prestamo.setEstado(EstadoPrestamo.PRESTADO);

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
            prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        }

        prestamoRepository.save(prestamo);

        PrestamoRs response = new PrestamoRs();
        response.setMessage("Préstamo actualizado con éxito");
=======

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
>>>>>>> 708e27d9b1709601d41210363af62176929a3745
        return response;
    }

    @Override
<<<<<<< HEAD
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
=======
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
>>>>>>> 708e27d9b1709601d41210363af62176929a3745
