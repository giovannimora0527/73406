package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Autor> obtenerListadoAutores() {
        return this.autorRepository.findAllByOrderByFechaNacimientoDesc();
    }

    @Override
    public List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad)
            throws BadRequestException {
        List<Autor> listaAutores = this.autorRepository.findByNacionalidad(nacionalidad);
        listaAutores.forEach(elem -> {
            System.out.println("Nombre Autor => " + elem.getNombre());
        });

        if (listaAutores.isEmpty()) {
            throw new BadRequestException("No existen autores con esa nacionalidad.");
        }

        return listaAutores;
    }

    @Override
    public Autor obtenerAutorPorId(Integer autorId) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autorId);
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + autorId);
        }
        return optAutor.get();
    }

    @Override
    public AutorRs guardarAutorNuevo(AutorRq autor) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findByNombre(autor.getNombre());

        if (optAutor.isPresent()) {
            throw new BadRequestException("El autor ya existe con el nombre "
                    + autor.getNombre()
                    + ", verifique e intente de nuevo.");
        }

        this.autorRepository.save(this.convertirAutorRqToAutor(autor));

        AutorRs rta = new AutorRs();
        rta.setMessage("Se ha guardado el autor con éxito.");
        return rta;
    }

    private Autor convertirAutorRqToAutor(AutorRq autor) {
        Autor a = new Autor();
        a.setNombre(autor.getNombre());
        a.setNacionalidad(autor.getNacionalidad());
        a.setFechaNacimiento(autor.getFechaNacimiento());
        return a;
    }

    @Override
    public AutorRs actualizarAutor(Autor autor) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autor.getAutorId());
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No existe el autor.");
        }

        AutorRs rta = new AutorRs();
        rta.setMessage("El autor se actualizó correctamente.");
        Autor autorActual = optAutor.get();

        if (!cambioObjeto(autorActual, autor)) {
            return rta;
        }

        if (!autor.getNombre().equals(autorActual.getNombre())) {
            if (this.autorRepository.existsByNombre(autor.getNombre())) {
                throw new BadRequestException("El nombre del autor: " + autor.getNombre()
                        + ", existe en la bd. Verifique e intente de nuevo.");
            }
        }

        autorActual.setNombre(autor.getNombre());
        autorActual.setNacionalidad(autor.getNacionalidad());
        autorActual.setFechaNacimiento(autor.getFechaNacimiento());

        this.autorRepository.save(autorActual);
        return rta;
    }

    private boolean cambioObjeto(Autor autorActual, Autor userFront) {
        return !autorActual.getNombre().equals(userFront.getNombre())
                || !autorActual.getNacionalidad().equals(userFront.getNacionalidad())
                || !autorActual.getFechaNacimiento().equals(userFront.getFechaNacimiento());
    }
}
