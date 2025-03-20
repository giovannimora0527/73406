package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.AutorRepository;
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
    
    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Libro> listarLibros() throws BadRequestException {
       return this.libroRepository.findAll();
    }

    @Override
    /**
    * Obtiene un libro por su ID.
    *
    * @param libroId el ID del libro a buscar.
    * @return el libro encontrado.
    * @throws BadRequestException si el libro no existe.
    */
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {        
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el ID = " + libroId);
        }
        return optLibro.get();
    }

    @Override
    /**
    * Lista los libros de un autor específico.
    *
    * @param idAutor el ID del autor cuyos libros se desean listar.
    * @return una lista de libros del autor.
    * @throws BadRequestException si el autor no existe o no tiene libros registrados.
    */
    public List<Libro> listarLibrosPorAutor(Long idAutor) throws BadRequestException {
    // Obtener el autor y validar si existe
    Autor autor = autorRepository.findById(idAutor)
        .orElseThrow(() -> new BadRequestException("No se encuentra un autor con el ID = " + idAutor));

    // Obtener los libros asociados al autor
    return libroRepository.findByAutor(autor);
    }
    

    @Override
    /**
    * Obtiene un libro a partir de su título.
    *
    * @param titulo el título del libro a buscar.
    * @return el libro encontrado.
    * @throws BadRequestException si el libro no existe.
    */
    public Libro obtenerLibroPorTitulo(String titulo) throws BadRequestException {
        return libroRepository.findByTitulo(titulo)
                .orElseThrow(() -> new BadRequestException("No se encontró un libro con el nombre: " + titulo));
    }
    
    
    @Override
    
   /**
    * Lista los libros publicados dentro de un rango de años.
    *
    * @param anioInicio el año de inicio del rango.
    * @param anioFin el año de fin del rango.
    * @return una lista de libros publicados dentro del rango especificado.
    * @throws BadRequestException si la fecha de inicio es mayor que la final
    *         o si no se encuentran libros en el rango.
    */
    public List<Libro> listarLibrosPoranioPublicacion(int anioInicio, int anioFin) throws BadRequestException {
    if (anioInicio > anioFin) {
        throw new BadRequestException("La fecha de inicio no puede ser mayor que la fecha final.");
    }
    
    List<Libro> libros = libroRepository.findByAnioPublicacionBetween(anioInicio, anioFin);
    
    if (libros.isEmpty()) {
        throw new BadRequestException("No se encontraron libros en el rango de fechas proporcionado.");
    }
    
    return libros;
}
}
