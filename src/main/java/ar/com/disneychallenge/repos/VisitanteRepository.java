package ar.com.disneychallenge.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.disneychallenge.entities.Visitante;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante, Integer> {

    Visitante findByVisitanteId(Integer id);
}
