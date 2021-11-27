package ar.com.disneychallenge.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.disneychallenge.entities.Pelicula;
import ar.com.disneychallenge.models.request.InfoPeliNueva;
import ar.com.disneychallenge.models.response.GenericResponse;
import ar.com.disneychallenge.models.response.MovieResponse;
import ar.com.disneychallenge.services.PeliculaService;

@RestController
public class PeliculaController {

    @Autowired
    PeliculaService service;

    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponse>> traerPeliculas() {

        return ResponseEntity.ok(service.traerPeliculasModel());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> buscarPreguntaPorId(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();

        if(service.existePorId(id)) {

            return ResponseEntity.ok(service.buscarPeliculaPorId(id));

        } else {

            respuesta.isOk = false;
            respuesta.message = "La pelicula no existe";
        
            return ResponseEntity.badRequest().body(respuesta);
        }
    } 

    @PostMapping("/movies")
    public ResponseEntity<?> crearPregunta(@RequestBody InfoPeliNueva peliculaNueva) {

        GenericResponse respuesta = new GenericResponse();

        Pelicula pelicula = new Pelicula();

        if(service.crearPelicula(peliculaNueva.imagenPelicula, peliculaNueva.titulo, peliculaNueva.fechaCreacion,
                                    peliculaNueva.calificacion, peliculaNueva.generoId) != null) {

            respuesta.id = pelicula.getPeliculaId();
            respuesta.isOk = true;
            respuesta.message = "La pelicula fue creada con exito";
    
            return ResponseEntity.ok(respuesta);

        } else {

            respuesta.isOk = false;
            respuesta.message = "La pelicula ya existe";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }  
}
