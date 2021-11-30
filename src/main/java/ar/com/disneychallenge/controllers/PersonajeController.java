package ar.com.disneychallenge.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.disneychallenge.entities.Personaje;
import ar.com.disneychallenge.models.request.InfoPersonajeNuevo;
import ar.com.disneychallenge.models.response.CharacterResponse;
import ar.com.disneychallenge.models.response.GenericResponse;
import ar.com.disneychallenge.services.PersonajeService;

@RestController
public class PersonajeController {

    @Autowired
    PersonajeService service;

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
    
}
