package ar.com.disneychallenge.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.disneychallenge.entities.Genero;
import ar.com.disneychallenge.entities.Pelicula;
import ar.com.disneychallenge.models.response.MovieResponse;
import ar.com.disneychallenge.repos.PeliculaRepository;

@Service
public class PeliculaService {

    @Autowired
    PeliculaRepository repo;

    @Autowired
    GeneroService generoService;

    public Pelicula buscarPeliculaPorId(Integer peliculaId) {

        Optional<Pelicula> resultado = repo.findById(peliculaId);

        if(resultado.isPresent())
            return resultado.get();

        return null;
    }

    public List<Pelicula> traerPeliculas() {

        return repo.findAll();
    }

    public List<MovieResponse> traerPeliculasModel() {

        List<MovieResponse> pelis = new ArrayList<>();

        for(Pelicula pelicula : this.traerPeliculas()) {

            MovieResponse movie = new MovieResponse();

            movie.imagenPelicula = pelicula.getImagenPelicula();
            movie.titulo = pelicula.getTitulo();
            movie.fechaCreacion = pelicula.getFechaCreacion();

            pelis.add(movie);
        }

        return pelis;
    }

    public Pelicula crearPelicula(String imagenPelicula, String titulo, Date fechaCreacion, Integer calificacion,
                                    Integer generoId) {

        if(!existePorTitulo(titulo)) {

            Pelicula pelicula = new Pelicula();
        
            pelicula.setImagenPelicula(imagenPelicula);
            pelicula.setTitulo(titulo);
            pelicula.setFechaCreacion(fechaCreacion);
            pelicula.setCalificacion(calificacion);

            Genero genero = generoService.buscarGeneroPorId(generoId);

            pelicula.setGenero(genero);

            return repo.save(pelicula);
        }

        return null;
    }

    public boolean existePorId(int id) {

        Pelicula pelicula = repo.findById(id);
        return pelicula != null;
    }

    public boolean existePorTitulo(String titulo) {

        Pelicula pelicula = repo.findByTitulo(titulo);
        return pelicula != null;
    } 
    
    public boolean eliminarPeliculaPorId(Integer id) {

        boolean res = false;

        if(existePorId(id)) { 

            repo.deleteById(id);

            res = (!existePorId(id));
        }

        return res;  
    }

    public void actualizarPelicula(Pelicula pelicula) {

        repo.save(pelicula);
    }

    public Pelicula traerPeliculaPorTitulo(String titulo) {

        return repo.findByTitulo(titulo);
    }

    public List<Pelicula> obtenerPelisPorGeneroId(Integer generoId) {
                
        Genero genero = generoService.buscarGeneroPorId(generoId);

        return genero.getPeliculas();
    }
}
