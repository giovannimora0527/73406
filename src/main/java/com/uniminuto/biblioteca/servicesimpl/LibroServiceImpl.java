package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */

/**
 * Implementación de la interfaz LibroService para gestionar los libros.
 * Proporciona métodos para listar, buscar y obtener libros.
 */
@Service
public class LibroServiceImpl implements LibroService {
    
    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Libro obtenerLibroId(Integer libroId) {
        return libroRepository.findById(libroId)
                .orElseThrow(() -> new EntityNotFoundException("No se encuentra el libro con ID: " + libroId));
    }
    
    /**
    * Lista todos los libros asociados a un autor específico.
  
    Este método verifica si el autor existe antes de buscar los libros. 
    Si el autor no está registrado en la base de datos, lanza una excepción **/
    
    @Override
    public List<Libro> listarLibrosPorAutor(Integer autorId) {
        // Verificar si el autor existe
        boolean existeAutor = libroRepository.existsByAutor_AutorId(autorId);
        if (!existeAutor) {
            throw new EntityNotFoundException("No se encontró el autor con ID: " + autorId);
        }
       
            // Buscar libros por autor y retornar una lista vacía si no hay resultados
         return libroRepository.findByAutor_AutorId(autorId);// Si no hay libros, retorna lista vacía
           
    }
    
    
    /**
    * Obtiene un libro por su título.
    * 
    * Este método busca un libro en el repositorio utilizando el título proporcionado.
    * Si el libro no se encuentra en la base de datos, lanza una excepción indicando 
    * que no se encontró un libro con el título exacto.
    */
    
    @Override
     public Libro obtenerLibroPorTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró un libro con el título exacto: " + titulo));
    }

    
    /**
     * Obtiene una lista de libros publicados dentro de un rango de años especificado.
     * 
     * @param anioInicio Año de inicio del rango (incluido).
     * @param anioFin Año de finalización del rango (incluido).
     * @return Lista de libros cuyo año de publicación está dentro del rango.
     * @throws IllegalArgumentException si algún año es nulo o si el año de inicio es mayor al año final.
     */
    @Override
    public List<Libro> listarLibrosPorAnioPublicacion(Integer anioInicio, Integer anioFin) {
        if (anioInicio > anioFin) {
            throw new IllegalArgumentException("El año de inicio no puede ser mayor al año final.");
        }
        return libroRepository.findByAnioPublicacionBetween(anioInicio, anioFin);
    }
}