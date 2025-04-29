    package com.uniminuto.biblioteca.repository;

    import com.uniminuto.biblioteca.entity.Nacionalidad;
    import org.springframework.data.jpa.repository.JpaRepository;
    import java.util.Optional;
import org.springframework.stereotype.Repository;

    /**
     * Repositorio para la entidad Nacionalidad.
     * Proporciona métodos básicos para realizar operaciones CRUD.
     * 
     */
    @Repository
    public interface NacionalidadRepository extends
        JpaRepository<Nacionalidad, Integer> {
}