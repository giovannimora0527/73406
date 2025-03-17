package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface PrestamoRepository extends
        JpaRepository<Prestamo, Integer> {
    
}
