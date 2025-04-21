package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.NacionalidadRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

/**
 * Implementación de servicios para la gestión de autores.
 */
@Service
@RequiredArgsConstructor
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;
    private final NacionalidadRepository nacionalidadRepository;

    
    @Override
public List<Autor> listarAutor() throws BadRequestException {
    return obtenerListadoAutores();
}

    @Override
    public List<Autor> obtenerListadoAutores() throws BadRequestException {
        return autorRepository.findAll();
    }

    @Override
    public List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad) throws BadRequestException {
        return autorRepository.findByNacionalidadNombre(nacionalidad);
    }

    @Override
    public Autor obtenerAutorPorId(Integer autorId) throws BadRequestException {
        Optional<Autor> autor = autorRepository.findById(autorId);
        if (autor.isPresent()) {
            return autor.get();
        } else {
            throw new BadRequestException("El autor con ID " + autorId + " no fue encontrado.");
        }
    }

    @Override
    public AutorRs guardarAutorNuevo(AutorRq autorRq) throws BadRequestException {
        AutorRs respuesta = new AutorRs();

        Optional<Nacionalidad> nacionalidad = nacionalidadRepository.findById(Integer.valueOf(autorRq.getNacionalidadId()));
        if (nacionalidad.isEmpty()) {
            respuesta.setMessage("La nacionalidad con ID " + autorRq.getNacionalidadId() + " no fue encontrada.");
            return respuesta;
        }

        Autor autor = new Autor();
        autor.setNombre(autorRq.getNombre());
        autor.setFechaNacimiento(autorRq.getFechaNacimiento());
        autor.setNacionalidad(nacionalidad.get());

        autorRepository.save(autor);

        respuesta.setMessage("Autor creado correctamente con ID: " + autor.getAutorId());
        return respuesta;
    }

    @Override
    public AutorRs actualizarAutor(Autor autor) throws BadRequestException {
        AutorRs respuesta = new AutorRs();

        Optional<Autor> autorExistente = autorRepository.findById(autor.getAutorId());
        if (autorExistente.isEmpty()) {
            respuesta.setMessage("El autor con ID " + autor.getAutorId() + " no fue encontrado.");
            return respuesta;
        }

        autorRepository.save(autor);
        respuesta.setMessage("Autor actualizado correctamente.");
        return respuesta;
    }
}
