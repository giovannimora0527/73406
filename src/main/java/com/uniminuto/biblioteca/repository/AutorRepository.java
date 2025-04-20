package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {

    List<Autor> findByNacionalidad(Nacionalidad nacionalidad);

    Autor findByNombre(String nombre);

    List<Autor> findByFechaNacimientoBetween(LocalDate fechaNacimientoInicial, LocalDate fechaNacimientoFin);

    List<Autor> findAllByOrderByFechaNacimientoAsc();

    List<Autor> findAllByOrderByFechaNacimientoDesc();
    
    List<Autor> findByNacionalidadNombre(String nombre);
    List<Autor> findByNacionalidadNombreIgnoreCase(String nombre);
    
    boolean existsByNombre(String nombre);
    
    boolean existsByFechaNacimiento(String FechaNacimiento);
}
