package ar.com.disneychallenge.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.disneychallenge.entities.Personaje;

public interface PersonajeRepository extends JpaRepository<Personaje, Integer> {

    Personaje findByNombre(String nombre);
    Personaje findById(int id);
    List<Personaje> findByEdad(Integer edad);
    List<Personaje> findByPeso(Integer peso);
}
