package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de gestión de autores.
 * 
 * Proporciona la lógica para obtener información sobre los autores 
 * desde la base de datos.
 * 
 * @author lmora
 */
@Service
public class AutorServiceImpl implements AutorService {
    /**
     * Repositorio para el acceso a la base de datos de autores.
     */
    @Autowired
    private AutorRepository autorRepository;
    
    /**
     * Obtiene una lista de todos los autores ordenados por fecha de nacimiento en orden descendente.
     * 
     * @return Lista de autores ordenados por fecha de nacimiento de más reciente a más antigua.
     */
    @Override
    public List<Autor> obtenerListadoAutores() {
        return this.autorRepository.findAllByOrderByFechaNacimientoDesc();
    }
    
    
    /**
     * Obtiene una lista de autores filtrados por su nacionalidad.
     * 
     * @param nacionalidad Nacionalidad de los autores a buscar.
     * @return Lista de autores con la nacionalidad especificada.
     * @throws BadRequestException Si no se encuentran autores con esa nacionalidad.
     */
    @Override
    public List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad)
            throws BadRequestException {
        this.autorRepository.findByNacionalidad(nacionalidad).forEach(elem -> {
            System.out.println("Nombre Autor => " + elem.getNombre());
        });
        List<Autor> listaAutores = this.autorRepository.findByNacionalidad(nacionalidad);
        if (listaAutores.isEmpty()) {
            throw new BadRequestException("No existen autores con esa nacionalidad.");
        }
        
        return listaAutores;
    }
    
     /**
     * Obtiene un autor específico por su identificador único (ID).
     * 
     * @param idAutor ID del autor a buscar.
     * @return Autor correspondiente al ID proporcionado.
     * @throws BadRequestException Si no se encuentra el autor con el ID dado.
     */
    @Override
    public Autor obtenerAutorPorId(Long idAutor) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(idAutor);
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + idAutor);
        }
        return optAutor.get();
    }

}