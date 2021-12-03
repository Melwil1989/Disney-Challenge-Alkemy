package ar.com.disneychallenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.disneychallenge.entities.Visitante;
import ar.com.disneychallenge.repos.VisitanteRepository;

@Service
public class VisitanteService {

    @Autowired
    VisitanteRepository repo;

    public void crearVisitante(Visitante visitante) {
        repo.save(visitante);
    }

    public Visitante buscarPorId(Integer id) {
        return repo.findByVisitanteId(id);
    }
}
