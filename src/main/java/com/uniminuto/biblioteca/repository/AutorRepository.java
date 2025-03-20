package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@code Autor}.
 * 
 * Proporciona métodos para realizar operaciones de consulta sobre la 
 * tabla de autores en la base de datos.
 * 
 * Extiende {@link JpaRepository} para aprovechar los métodos de 
 * persistencia predeterminados.
 * 
 * @author lmora
 */
@Repository
public interface AutorRepository extends
        JpaRepository<Autor, Long>  {
    /**
     * Obtiene una lista de autores cuya fecha de nacimiento se encuentra 
     * dentro del rango especificado.
     * 
     * @param fechaNacimientoInicial Fecha de inicio del rango.
     * @param fechaNacimientoFin Fecha de fin del rango.
     * @return Lista de autores nacidos en el rango de fechas especificado.
     */
    List<Autor> findByFechaNacimientoBetween(LocalDate fechaNacimientoInicial,
            LocalDate fechaNacimientoFin);
    
    /**
     * Obtiene una lista de autores según su nacionalidad.
     * 
     * @param nacionalidad Nacionalidad de los autores a buscar.
     * @return Lista de autores con la nacionalidad especificada.
     */
    List<Autor> findByNacionalidad(String nacionalidad);
    
    /**
     * Obtiene una lista de todos los autores ordenados por fecha de nacimiento 
     * en orden ascendente.
     * 
     * @return Lista de autores ordenada por fecha de nacimiento (ascendente).
     */
    List<Autor> findAllByOrderByFechaNacimientoAsc();
    
    /**
     * Obtiene una lista de todos los autores ordenados por fecha de nacimiento 
     * en orden descendente.
     * 
     * @return Lista de autores ordenada por fecha de nacimiento (descendente).
     */
    
    List<Autor> findAllByOrderByFechaNacimientoDesc();
}
