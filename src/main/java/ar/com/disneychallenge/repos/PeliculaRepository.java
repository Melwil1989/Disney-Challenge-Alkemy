package ar.com.disneychallenge.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.disneychallenge.entities.Pelicula;

public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {
    
}
