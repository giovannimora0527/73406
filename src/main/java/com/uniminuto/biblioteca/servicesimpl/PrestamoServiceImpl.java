package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestión de préstamos de la biblioteca
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    /**
     * Obtiene todos los préstamos registrados
     * Actualiza automáticamente los estados vencidos antes de retornar
     * @return Lista de objetos PrestamoRs con la información de cada préstamo
     */
    @Override
    public List<PrestamoRs> getPrestamos() {
        // Actualizar estados vencidos antes de consultar
        this.actualizarEstadosVencidos();
        
        return prestamoRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Registra un nuevo préstamo en el sistema
     * DESCUENTA las existencias del libro automáticamente
     * @param rq Objeto con la información del préstamo a crear
     * @return Objeto PrestamoRs con la información del préstamo creado
     */
    @Override
    @Transactional
    public PrestamoRs guardarPrestamo(PrestamoRq rq) {
        // Validar que existan el usuario y el libro
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(rq.getIdUsuario());
        Optional<Libro> libroOpt = libroRepository.findById(rq.getIdLibro());

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado.");
        }
        
        if (libroOpt.isEmpty()) {
            throw new RuntimeException("Libro no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();
        Libro libro = libroOpt.get();

        // CAMBIO IMPORTANTE: Verificar disponibilidad por existencias directas
        if (libro.getExistencias() <= 0) {
            throw new RuntimeException("El libro no tiene existencias disponibles para préstamo.");
        }

        // Validar que la fecha de devolución sea al menos un día después de la fecha actual
        LocalDate fechaActual = LocalDate.now();
        if (rq.getFechaDevolucion() == null || 
            rq.getFechaDevolucion().isBefore(fechaActual.plusDays(1))) {
            throw new RuntimeException("La fecha de devolución debe ser al menos un día posterior a la fecha actual.");
        }

        // CAMBIO IMPORTANTE: DESCONTAR existencias del libro
        libro.setExistencias(libro.getExistencias() - 1);
        libroRepository.save(libro);

        // Crear el nuevo préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(fechaActual); // Fecha automática
        prestamo.setFechaDevolucion(rq.getFechaDevolucion());
        prestamo.setEstado(Prestamo.EstadoPrestamo.PRESTADO); // Estado inicial siempre es PRESTADO

        return mapToResponse(prestamoRepository.save(prestamo));
    }

    /**
     * Actualiza un préstamo existente
     * Según el requerimiento, solo debe permitir actualizar la fecha de entrega
     * DEVUELVE las existencias al libro cuando se marca como devuelto
     * @param id ID del préstamo a actualizar
     * @param rq Objeto con la información del préstamo a actualizar
     * @return Objeto PrestamoRs con la información del préstamo actualizado
     */
    @Override
    @Transactional
    public PrestamoRs actualizarPrestamo(Long id, PrestamoRq rq) {
        // Buscar el préstamo existente
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(id);
        
        if (prestamoOpt.isEmpty()) {
            throw new RuntimeException("Préstamo no encontrado.");
        }
        
        Prestamo prestamo = prestamoOpt.get();
        
        // Solo permitir actualizar si el préstamo está activo (PRESTADO o VENCIDO)
        if (prestamo.getEstado() == Prestamo.EstadoPrestamo.DEVUELTO) {
            throw new RuntimeException("No se puede modificar un préstamo que ya fue devuelto.");
        }
        
        // Actualizar fecha de entrega (único campo editable según requerimiento)
        if (rq.getFechaEntrega() != null) {
            // Validar que la fecha de entrega no sea anterior a la fecha de préstamo
            if (rq.getFechaEntrega().isBefore(prestamo.getFechaPrestamo())) {
                throw new RuntimeException("La fecha de entrega no puede ser anterior a la fecha de préstamo.");
            }
            
            prestamo.setFechaEntrega(rq.getFechaEntrega());
            
            // CAMBIO IMPORTANTE: DEVOLVER existencias al libro cuando se marca como devuelto
            Libro libro = prestamo.getLibro();
            libro.setExistencias(libro.getExistencias() + 1);
            libroRepository.save(libro);
            
            // CAMBIO IMPORTANTE: Cuando se registra fecha de entrega, SIEMPRE es DEVUELTO
            // El estado VENCIDO solo aplica cuando NO se ha devuelto y pasó la fecha límite
            prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
        }
        
        return mapToResponse(prestamoRepository.save(prestamo));
    }

    /**
     * Obtiene la lista de libros disponibles para préstamo
     * CAMBIO: Ahora se basa en existencias directas del libro
     * @return Lista de libros disponibles
     */
    @Override
    public List<Libro> getLibrosDisponibles() {
        // Filtrar libros que tengan existencias mayores a 0
        return libroRepository.findByExistenciasGreaterThan(0);
    }

    /**
     * NUEVO MÉTODO: Tarea programada que se ejecuta diariamente para actualizar estados vencidos
     * Se ejecuta todos los días a las 00:01 AM
     */
    @Scheduled(cron = "0 1 0 * * ?") // Ejecutar diariamente al inicio del día
    @Transactional
    public void actualizarEstadosVencidosScheduled() {
        this.actualizarEstadosVencidos();
    }

    /**
     * NUEVO MÉTODO: Actualiza automáticamente los préstamos vencidos
     * Cambia el estado a VENCIDO si la fecha actual es posterior a la fecha de devolución
     * y el préstamo sigue en estado PRESTADO
     */
    @Transactional
    public void actualizarEstadosVencidos() {
        LocalDate fechaActual = LocalDate.now();
        
        // Buscar préstamos en estado PRESTADO que ya pasaron su fecha de devolución
        List<Prestamo> prestamosVencidos = prestamoRepository.findAll().stream()
                .filter(prestamo -> 
                    prestamo.getEstado() == Prestamo.EstadoPrestamo.PRESTADO &&
                    prestamo.getFechaDevolucion().isBefore(fechaActual))
                .collect(Collectors.toList());
        
        // Actualizar estado a VENCIDO
        for (Prestamo prestamo : prestamosVencidos) {
            prestamo.setEstado(Prestamo.EstadoPrestamo.VENCIDO);
            prestamoRepository.save(prestamo);
        }
        
        if (!prestamosVencidos.isEmpty()) {
            System.out.println("Se actualizaron " + prestamosVencidos.size() + " préstamos a estado VENCIDO");
        }
    }

    /**
     * Mapea un objeto Prestamo a su representación PrestamoRs
     * @param prestamo Objeto Prestamo a mapear
     * @return Objeto PrestamoRs con la información formateada
     */
    private PrestamoRs mapToResponse(Prestamo prestamo) {
        PrestamoRs rs = new PrestamoRs();
        rs.setIdPrestamo(prestamo.getIdPrestamo());
        rs.setNombreUsuario(prestamo.getUsuario().getNombre());
        rs.setTituloLibro(prestamo.getLibro().getTitulo());
        rs.setFechaPrestamo(prestamo.getFechaPrestamo());
        rs.setFechaDevolucion(prestamo.getFechaDevolucion());
        rs.setFechaEntrega(prestamo.getFechaEntrega());
        rs.setEstado(prestamo.getEstado().toString());
        return rs;
    }
}