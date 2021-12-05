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

    public Personaje buscarPersonajePorId(Integer id) {

        Optional<Personaje> resultado = repo.findById(id);

        if(resultado.isPresent())
            return resultado.get();

        return null;
    }

    public boolean existePorId(int id) {

        Personaje personaje = repo.findById(id);
        return personaje != null;
    }

    public boolean eliminarPersonajePorId(Integer id) {

        boolean res = false;

        if(existePorId(id)) { 

            repo.deleteById(id);

            res = (!existePorId(id));
        }

        return res;  
    }

    public void actualizarPersonaje(Personaje personaje) {

        repo.save(personaje);
    }

    public List<Personaje> obtenerPersonajesPorPeliId(Integer peliculaId) {
                
        Pelicula pelicula = peliculaService.buscarPeliculaPorId(peliculaId);

        return pelicula.getPersonajes();
    }

    public Personaje traerPersonajePorNombre(String nombre) {

        return repo.findByNombre(nombre);
    }

    public List<Personaje> traerPersonajesPorEdad(Integer edad) {

        return repo.findByEdad(edad);
    }

    public boolean existePorEdad(Integer edad) {

        List<Personaje> personajes = repo.findByEdad(edad);
        return personajes != null;
    }

    public boolean existePorPeso(Integer peso) {

        List<Personaje> personajes = repo.findByPeso(peso);
        return personajes != null;
    }

    public List<Personaje> traerPersonajesPorPeso(Integer peso) {

        return repo.findByPeso(peso);
    }
}
