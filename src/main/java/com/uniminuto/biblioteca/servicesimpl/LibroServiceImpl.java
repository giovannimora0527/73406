package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository; // Repositorio de Autor agregado

    public LibroServiceImpl(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Optional<Libro> obtenerLibroPorId(Integer id) {
        return libroRepository.findById(id);
    }

    @Override
    public List<Libro> obtenerLibrosPorAutor(Integer autorId) { 
        // Verificar si el autor existe
        if (!autorRepository.existsById(autorId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El autor con ID " + autorId + " no existe.");
        }

        // Buscar libros del autor
        List<Libro> libros = libroRepository.findByAutor_Id(autorId);

        // Si el autor no tiene libros, lanzar 400 Bad Request
        if (libros.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay libros para el autor con ID " + autorId);
        }

        return libros;
    }

    @Override
    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public void eliminarLibro(Integer id) {
        libroRepository.deleteById(id);
    }

    @Override
    public Optional<Libro> obtenerLibroPorNombre(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }

    @Override
    public List<Libro> listarLibrosPorRangoFechas(Integer inicio, Integer fin) {
        return libroRepository.findByRangoFechas(inicio, fin);
    }

    @Override
    public List<Libro> listarLibrosPorAutor(Integer autorId) {
        return obtenerLibrosPorAutor(autorId); 
    }
}
