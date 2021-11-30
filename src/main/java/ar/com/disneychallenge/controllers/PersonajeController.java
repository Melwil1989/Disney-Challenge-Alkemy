package ar.com.disneychallenge.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.disneychallenge.entities.Pelicula;
import ar.com.disneychallenge.entities.Personaje;
import ar.com.disneychallenge.models.request.InfoPersonajeActualizado;
import ar.com.disneychallenge.models.request.InfoPersonajeNuevo;
import ar.com.disneychallenge.models.response.CharacterResponse;
import ar.com.disneychallenge.models.response.GenericResponse;
import ar.com.disneychallenge.models.response.InfoPersonajeResponse;
import ar.com.disneychallenge.services.PeliculaService;
import ar.com.disneychallenge.services.PersonajeService;

@RestController
public class PersonajeController {

    @Autowired
    PersonajeService service;

    @Autowired
    PeliculaService peliculaService;

    @PostMapping("/characters")
    public ResponseEntity<?> crearPersonaje(@RequestBody InfoPersonajeNuevo personajeNuevo) {

        GenericResponse respuesta = new GenericResponse();

        Personaje personaje = new Personaje();

        if(service.crearPersonaje(personajeNuevo.imagen, personajeNuevo.nombre, personajeNuevo.edad,
                                    personajeNuevo.peso, personajeNuevo.historia, personajeNuevo.peliculaId) != null) {
            
            respuesta.id = personaje.getPersonajeId();
            respuesta.isOk = true;
            respuesta.message = "El personaje ha sido creado con exito";

            return ResponseEntity.ok(respuesta);

        } else {

            respuesta.isOk = false;
            respuesta.message = "El personaje ya existe";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @GetMapping("/characters")
    public ResponseEntity<List<CharacterResponse>> traerPersonajes() {

        return ResponseEntity.ok(service.traerPersonajesModel());
    }

    @DeleteMapping("/characters/{id}")
    public ResponseEntity<?> eliminarPersonajePorId(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();

        if(service.eliminarPersonajePorId(id)) {

            respuesta.isOk = true;
            respuesta.message = "El personaje fue eliminado";

            return ResponseEntity.ok(respuesta);

        } else {

            respuesta.isOk = false;
            respuesta.message = "Ocurrio un error al intentar ejecutar la solicitud";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @PutMapping("/characters/{id}")
    public ResponseEntity<?> editarPersonaje(@PathVariable Integer id, @RequestBody InfoPersonajeActualizado personajeActualizado) {

        GenericResponse respuesta = new GenericResponse();

        if(service.existePorId(id)) {

            Personaje personaje = service.buscarPersonajePorId(id);

            personaje.setImagen(personajeActualizado.imagen);
            personaje.setNombre(personajeActualizado.nombre);
            personaje.setEdad(personajeActualizado.edad);
            personaje.setPeso(personajeActualizado.peso);
            personaje.setHistoria(personajeActualizado.historia);
            
            Pelicula pelicula = peliculaService.buscarPeliculaPorId(personajeActualizado.peliculaId);
            pelicula.agregarPersonaje(personaje);
            
            service.actualizarPersonaje(personaje);

            respuesta.isOk = true;
            respuesta.message = "El personaje ha sido actualizado con exito";

            return ResponseEntity.ok(respuesta);

        } else{

            respuesta.isOk = false;
            respuesta.message = "El personaje no existe";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<?> buscarPersonajePorId(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();

        if(service.existePorId(id)) {

            InfoPersonajeResponse personajeResponse = new InfoPersonajeResponse();

            Personaje personaje = service.buscarPersonajePorId(id);

            personajeResponse.imagen = personaje.getImagen();
            personajeResponse.nombre = personaje.getNombre();
            personajeResponse.edad = personaje.getEdad();
            personajeResponse.peso = personaje.getPeso();
            personajeResponse.historia = personaje.getHistoria();
            personajeResponse.peliculaId = personaje.getPelicula().getPeliculaId();

            return ResponseEntity.ok(personajeResponse);

        } else {

            respuesta.isOk = false;
            respuesta.message = "El personaje no existe";
        
            return ResponseEntity.badRequest().body(respuesta);
        }
    }
    
}
