package ar.com.disneychallenge.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.disneychallenge.entities.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    
}
