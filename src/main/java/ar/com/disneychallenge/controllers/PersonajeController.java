package ar.com.disneychallenge.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
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
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
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
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
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

    @GetMapping("/characters/movies/{peliculaId}")
    public ResponseEntity<?> traerPersonajesPorPeli(@PathVariable Integer peliculaId) {

        GenericResponse respuesta = new GenericResponse();

        if(peliculaService.existePorId(peliculaId)) {

            return ResponseEntity.ok(service.obtenerPersonajesPorPeliId(peliculaId));

        } else {

            respuesta.isOk = false;
            respuesta.message = "La pelicula ingresada no existe";

            return ResponseEntity.badRequest().body(respuesta);
        }
    } 
    
    @GetMapping("/api/characters/{nombre}")
    public ResponseEntity<?> traerPersonajePorNombre(@PathVariable String nombre) {

        GenericResponse respuesta = new GenericResponse();

        if(service.existePorNombre(nombre)) {

            return ResponseEntity.ok(service.traerPersonajePorNombre(nombre));

        } else {

            respuesta.isOk = false;
            respuesta.message = "El personaje no existe";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @GetMapping("/characters/age/{edad}")
    public ResponseEntity<?> traerPersonajePorEdad(@PathVariable Integer edad) {

        GenericResponse respuesta = new GenericResponse();

        if(service.existePorEdad(edad)) {

            return ResponseEntity.ok(service.traerPersonajesPorEdad(edad)); 

        } else {

            respuesta.isOk = false;
            respuesta.message = "No existe ningun personaje cuya edad coincida con la edad ingresada";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @GetMapping("/characters/weight/{peso}")
    public ResponseEntity<?> traerPersonajePorPeso(@PathVariable Integer peso) {

        GenericResponse respuesta = new GenericResponse();

        if(service.existePorPeso(peso)) {

            return ResponseEntity.ok(service.traerPersonajesPorPeso(peso)); 

        } else {

            respuesta.isOk = false;
            respuesta.message = "No existe ningun personaje cuyo peso coincida con el peso ingresado";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }
}
