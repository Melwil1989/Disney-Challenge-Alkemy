package ar.com.disneychallenge.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.disneychallenge.entities.Genero;
import ar.com.disneychallenge.entities.Pelicula;
import ar.com.disneychallenge.models.request.InfoPeliActualizada;
import ar.com.disneychallenge.models.request.InfoPeliNueva;
import ar.com.disneychallenge.models.response.GenericResponse;
import ar.com.disneychallenge.models.response.InfoPeliResponse;
import ar.com.disneychallenge.models.response.MovieResponse;
import ar.com.disneychallenge.services.GeneroService;
import ar.com.disneychallenge.services.PeliculaService;

@RestController
public class PeliculaController {

    @Autowired
    PeliculaService service;

    @Autowired
    GeneroService generoService;

    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponse>> traerPeliculas() {

        return ResponseEntity.ok(service.traerPeliculasModel());
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> buscarPeliculaPorId(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();

        if(service.existePorId(id)) {

            InfoPeliResponse peliResponse = new InfoPeliResponse();

            Pelicula pelicula = service.buscarPeliculaPorId(id);

            peliResponse.imagenPelicula = pelicula.getImagenPelicula();
            peliResponse.titulo = pelicula.getTitulo();
            peliResponse.fechaCreacion = pelicula.getFechaCreacion();
            peliResponse.calificacion = pelicula.getCalificacion();
            peliResponse.generoId = pelicula.getGenero().getGeneroId();
            peliResponse.personajes = pelicula.getPersonajes();

            return ResponseEntity.ok(peliResponse);

        } else {

            respuesta.isOk = false;
            respuesta.message = "La pelicula no existe";
        
            return ResponseEntity.badRequest().body(respuesta);
        }
    } 

    @PostMapping("/movies")
    public ResponseEntity<?> crearPelicula(@RequestBody InfoPeliNueva peliculaNueva) {

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
    
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> eliminarPeliculaPorId(@PathVariable Integer id) {

        GenericResponse respuesta = new GenericResponse();

        if(service.eliminarPeliculaPorId(id)) {

            respuesta.isOk = true;
            respuesta.message = "La pelicula fue eliminada";

            return ResponseEntity.ok(respuesta);

        } else {

            respuesta.isOk = false;
            respuesta.message = "Ocurrio un error al intentar ejecutar la solicitud";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<?> editarPelicula(@PathVariable Integer id, @RequestBody InfoPeliActualizada peliActualizada) {

        GenericResponse respuesta = new GenericResponse();

        if(service.existePorId(id)) {

            Pelicula pelicula = service.buscarPeliculaPorId(id);

            pelicula.setImagenPelicula(peliActualizada.imagenPelicula);
            pelicula.setTitulo(peliActualizada.titulo);
            pelicula.setFechaCreacion(peliActualizada.fechaCreacion);
            pelicula.setCalificacion(peliActualizada.calificacion);
            
            Genero genero = generoService.buscarGeneroPorId(peliActualizada.generoId);
            genero.agregarPelicula(pelicula);
            
            service.actualizarPelicula(pelicula);

            respuesta.isOk = true;
            respuesta.message = "La pelicula ha sido actualizada con exito";

            return ResponseEntity.ok(respuesta);

        } else{

            respuesta.isOk = false;
            respuesta.message = "La pelicula no existe";

            return ResponseEntity.badRequest().body(respuesta);
        }
    }
}
