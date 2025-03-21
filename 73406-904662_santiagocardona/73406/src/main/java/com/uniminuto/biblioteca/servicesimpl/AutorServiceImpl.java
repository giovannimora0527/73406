package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pilol1111
 */
@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;


    /**
     * @return Obtiene el listado total de autores en orden desc
     */
    @Override
    public List<Autor> obtenerListadoAutores() {
        return this.autorRepository.findAllByOrderByFechaNacimientoDesc();
    }


    /**
     * @param nacionalidad
     * @return Obtiene el autor por nacionaldad
     * @throws BadRequestException
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
     * @param autorId
     * @return Obtiene el autor por su id
     * @throws BadRequestException
     */
    @Override
    public Autor obtenerAutorPorId(Integer autorId) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autorId);
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + autorId);
        }
        return optAutor.get();
    }
}

