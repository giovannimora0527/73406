/**
 * Repositorio para gestionar las operaciones de acceso a los datos relacionados con los préstamos.
 * 
 * <p>Esta interfaz extiende de {@link JpaRepository}, proporcionando métodos CRUD estándar
 * para la entidad {@link Prestamo}. Los métodos definidos aquí permiten interactuar con la base de datos
 * para realizar operaciones sobre los registros de préstamos.</p>
 * 
 * <p>El repositorio está diseñado para trabajar con la entidad {@link Prestamo} y su identificador
 * de tipo {@link Integer}.</p>
 * 
 * @author kaleth
 * @version 1.0
 */
package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio que permite realizar operaciones CRUD sobre la entidad {@link Prestamo}.
 */
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
}