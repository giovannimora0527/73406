package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LibroService {
    
    /**
     * Listar todos los libros disponibles.
     * @return Lista de libros.
     */
    List<Libro> listarLibros();

    /**
     * Buscar un libro por su ID.
     * @param id ID del libro a buscar.
     * @return Libro encontrado o vacío si no existe.
     */
    Optional<Libro> obtenerLibroPorId(Integer id);

    /**
     * Listar libros de un autor específico.
     * @param autorId ID del autor.
     * @return Lista de libros del autor.
     */
    List<Libro> listarLibrosPorAutor(Integer autorId);

    /**
     * Guardar un nuevo libro en la base de datos.
     * @param libro Libro a guardar.
     * @return Libro guardado.
     */
    Libro guardarLibro(Libro libro);

    /**
     * Eliminar un libro por su ID.
     * @param id ID del libro a eliminar.
     */
    void eliminarLibro(Integer id);

    /**
     * Buscar un libro por su nombre (case-sensitive).
     * @param titulo
     * @return Libro encontrado o vacío si no existe.
     */
    Optional<Libro> obtenerLibroPorNombre(String titulo);
    

    List<Libro> listarLibrosPorRangoFechas(Integer inicio, Integer fin);

    public List<Libro> obtenerLibrosPorAutor(Integer autorId);



}