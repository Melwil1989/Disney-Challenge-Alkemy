package ar.com.disneychallenge.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.disneychallenge.entities.Pelicula;
import ar.com.disneychallenge.entities.Personaje;
import ar.com.disneychallenge.models.response.CharacterResponse;
import ar.com.disneychallenge.repos.PersonajeRepository;

@Service
public class PersonajeService {

    @Autowired
    PersonajeRepository repo;

    @Autowired
    PeliculaService peliculaService;

    public Personaje crearPersonaje(String imagen, String nombre, Integer edad, Integer peso, 
                                        String historia, Integer peliculaId) {
        
        if(!existePorNombre(nombre)) {

            Personaje personaje = new Personaje();

            personaje.setImagen(imagen);
            personaje.setNombre(nombre);
            personaje.setEdad(edad);
            personaje.setPeso(peso);
            personaje.setHistoria(historia);
            
            Pelicula pelicula = peliculaService.buscarPeliculaPorId(peliculaId);

            personaje.setPelicula(pelicula);

            return repo.save(personaje);
        }

        return null;
    }

    public boolean existePorNombre(String nombre) {

        Personaje personaje = repo.findByNombre(nombre);
        return personaje != null;
    }

    public List<Personaje> traerPersonajes() {

        return repo.findAll();
    }

    public List<CharacterResponse> traerPersonajesModel() {

        List<CharacterResponse> personajes = new ArrayList<>();

        for(Personaje personaje : this.traerPersonajes()) {

            CharacterResponse character = new CharacterResponse();

            character.imagen = personaje.getImagen();
            character.nombre = personaje.getNombre();

            personajes.add(character);
        }

        return personajes;
    }
}
