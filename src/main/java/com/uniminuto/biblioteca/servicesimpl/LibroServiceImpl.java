package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado"));
    }

     /**
     * Obtiene una lista de libros escritos por un autor específico.
     *
     * @param autorId Identificador único del autor.
     * @return Lista de libros asociados al autor.
     */
    @Override
    public List<Libro> obtenerLibrosPorAutor(Integer autorId) {
        return libroRepository.findByAutor_AutorId(autorId);
    }

    /**
     * Busca un libro por su título.
     *
     * @param titulo Título del libro a buscar.
     * @return El libro encontrado si existe en la base de datos.
     * @throws ResponseStatusException Si no se encuentra el libro con el título especificado.
     */
    @Override
    public Libro obtenerLibroPorNombre(String titulo) {
        return libroRepository.findByTitulo(titulo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado con el título: " + titulo));
    }

    /**
     * Obtiene una lista de libros publicados dentro de un rango de años.
     *
     * @param anioInicio Año de inicio del rango.
     * @param anioFin Año final del rango.
     * @return Lista de libros publicados entre los años especificados.
     * @throws ResponseStatusException Si el año de inicio es mayor que el año final.
     */
    @Override
    public List<Libro> obtenerLibrosPorRangoAnios(Integer anioInicio, Integer anioFin) {
        if (anioInicio > anioFin) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El año de inicio no puede ser mayor que el año final.");
        }
        return libroRepository.findByAnioPublicacionBetween(anioInicio, anioFin);
    }
}
