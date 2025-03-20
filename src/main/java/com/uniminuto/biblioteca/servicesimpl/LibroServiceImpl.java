package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author lmora
 */
@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Libro obtenerLibroId(Integer libroId) {
        return libroRepository.findById(libroId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra el libro con id = " + libroId));
    }

    @Override
    public Libro obtenerLibroPorNombre(String titulo) {
        return libroRepository.findByTitulo(titulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encuentra el libro con el nombre = " + titulo));
    }

    @Override
    public List<Libro> listarLibrosPorFecha(String fechaInicio, String fechaFin) {
        try {
            Integer anioInicio = Integer.valueOf(fechaInicio);
            Integer anioFin = Integer.valueOf(fechaFin);
            return libroRepository.findByAnioPublicacionBetween(anioInicio, anioFin);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los valores de fecha deben ser números enteros válidos.");
        }
    }

    @Override
    public List<Libro> obtenerLibrosPorAutorId(Integer autorId) {
        return libroRepository.findLibrosByAutorId(autorId);
    }
}
