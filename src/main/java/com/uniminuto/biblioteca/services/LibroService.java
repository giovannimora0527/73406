package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Libro;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.apache.coyote.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author lmora
 */
public interface LibroService {
    /**
     * Lista todos los libros.
     * @return Lista de libros registrados.
     * @throws BadRequestException excepcion.
     */
    List<Libro> listarLibros() throws BadRequestException;
    
    /**
     * Obtiene un libro dado su id.
     * @param libroId Id del libro.
     * @return Libro.
     * @throws BadRequestException excepcion.
     */
    Libro obtenerLibroId(Integer libroId) throws BadRequestException;
    
    /**
     * Obtiene los libros registrados dado un autor.
     * @param autorId Id del autor.
     * @return lista de libros.
     * @throws BadRequestException excepcion.
     */
    List<Libro> obtenerLibrosPorAutor(Integer autorId) throws BadRequestException;
    
    
    /**
     * Obtiene un libro dado su nombre.
     * @param nombreLibro Nombre del libro.
     * @return Libro.
     * @throws BadRequestException excepcion.
     */
    Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException;
    
    /**
     * Obtiene el listado de libros dado la fecha de publicacion.
     * @param anioIni Año inicial de la consulta.
     * @param anioFin Año final de la consulta.
     * @return Lista de libros que cumplen con el criterio.
     * @throws BadRequestException excepcion.
     */
    List<Libro> obtenerLibroXRangoPublicacion(Integer anioIni, 
            Integer anioFin) throws BadRequestException;
    
    
    
    // Nuevo método para carga masiva
    /**
     * Carga libros desde un archivo CSV.
     * @param archivo Archivo CSV con los datos de los libros.
     * @return Mapa con el resultado del procesamiento.
     * @throws BadRequestException excepcion.
     */
    Map<String, Object> cargarLibrosDesdeCSV(MultipartFile archivo) throws BadRequestException;
}
