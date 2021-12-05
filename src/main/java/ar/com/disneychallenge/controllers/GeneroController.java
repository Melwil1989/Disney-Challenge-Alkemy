package ar.com.disneychallenge.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import ar.com.disneychallenge.entities.Genero;
import ar.com.disneychallenge.models.response.GenericResponse;
import ar.com.disneychallenge.services.GeneroService;

@RestController
public class GeneroController {

    @Autowired
    GeneroService service;

    @PostMapping("/genres")
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
    public ResponseEntity<GenericResponse> crearGenero(@RequestBody Genero genero) {
        
        GenericResponse respuesta = new GenericResponse();

        if(service.crearGenero(genero)) {

            respuesta.id = genero.getGeneroId();
            respuesta.isOk = true;
            respuesta.message = "Genero creado con exito";

            return ResponseEntity.ok(respuesta);

        } else {

            respuesta.isOk = false;
            respuesta.message = "El genero ya existe";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genero>> traerGeneros() {

        return ResponseEntity.ok(service.traerGeneros());
    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<?> traerGeneroPorId(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();

        if(service.existePorId(id)) {

            return ResponseEntity.ok(service.buscarGeneroPorId(id));

        } else {

            respuesta.isOk = false;
            respuesta.message = "El genero no existe";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @DeleteMapping("/genres/{id}")
    @PreAuthorize("hasAuthority('CLAIM_userType_STAFF')")
    public ResponseEntity<?> eliminarGeneroPorId(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();

        if(service.eliminarGeneroPorId(id)) {

            respuesta.isOk = true;
            respuesta.message = "El genero fue eliminado";

            return ResponseEntity.ok(respuesta);

        } else {

            respuesta.isOk = false;
            respuesta.message = "Ocurrio un error al intentar ejecutar la solicitud";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }  
}
