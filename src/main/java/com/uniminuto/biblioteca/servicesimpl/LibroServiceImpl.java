package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class LibroServiceImpl implements LibroService {
    
    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Libro> listarLibros() throws BadRequestException {
       return this.libroRepository.findAll();
    }

    @Override
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {        
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = " 
                    + libroId);
        }
        return optLibro.get();
    }

    // Método para buscar por nombre (case-sensitive)
    @Override
    public Libro obtenerLibroPorNombre(String nombre) throws BadRequestException {
    Optional<Libro> optLibro = this.libroRepository.findByNombre(nombre);
    if (!optLibro.isPresent()) {
        throw new BadRequestException("No se encuentra el libro con el nombre = " + nombre);
    }
    return optLibro.get();
    }
    @Override
    public List<Libro> listarLibrosPorFecha(String fechaInicio, String fechaFin) {
        return libroRepository.findByFechaPublicacionBetween(fechaInicio, fechaFin);
    }
}