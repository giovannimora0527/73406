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
import java.util.Optional;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Prestamo> obtenerListadoPrestamos() {
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo obtenerPrestamoPorId(Integer idPrestamo) throws BadRequestException {
        Optional<Prestamo> optPrestamo = prestamoRepository.findById(idPrestamo);
        if (!optPrestamo.isPresent()) {
            throw new BadRequestException("No se encuentra el préstamo con el ID " + idPrestamo);
        }
        return optPrestamo.get();
    }

    @Override
    public PrestamoRs registrarPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        // Verificar que el usuario existe
        Usuario usuario = usuarioRepository.findById(prestamoRq.getIdUsuario())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));
        
        // Verificar que el libro existe
        Libro libro = libroRepository.findById(prestamoRq.getIdLibro())
                .orElseThrow(() -> new BadRequestException("Libro no encontrado"));
        
        // Verificar que el libro no esté prestado actualmente
        if (prestamoRepository.existsByLibroIdLibroAndEstado(prestamoRq.getIdLibro(), EstadoPrestamo.PRESTADO)) {
            throw new BadRequestException("El libro ya está prestado");
        }
        
        // Verificar que el usuario no tenga más de 3 préstamos activos
        if (prestamoRepository.countByUsuarioIdUsuarioAndEstado(prestamoRq.getIdUsuario(), EstadoPrestamo.PRESTADO) >= 3) {
            throw new BadRequestException("El usuario ya tiene el máximo de 3 préstamos activos");
        }
        
        // Crear préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
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
        // Verificar que el préstamo existe
        Prestamo prestamo = obtenerPrestamoPorId(prestamoRq.getIdPrestamo());
        
        // Actualizar datos
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
        return response;
    }

    @Override
    public PrestamoRs registrarDevolucion(Integer idPrestamo) throws BadRequestException {
        Prestamo prestamo = obtenerPrestamoPorId(idPrestamo);
        
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
    public List<Prestamo> obtenerPrestamosPorUsuario(Integer idUsuario) throws BadRequestException {
        if (!usuarioRepository.existsById(idUsuario)) {
            throw new BadRequestException("Usuario no encontrado");
        }
        return prestamoRepository.findByUsuarioIdUsuario(idUsuario);
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorLibro(Integer idLibro) throws BadRequestException {
        if (!libroRepository.existsById(idLibro)) {
            throw new BadRequestException("Libro no encontrado");
        }
        return prestamoRepository.findByLibroIdLibro(idLibro);
    }

    @Override
    public List<Prestamo> obtenerPrestamosVencidos() {
        return prestamoRepository.findByFechaDevolucionBeforeAndEstado(LocalDate.now(), EstadoPrestamo.PRESTADO);
    }
}
