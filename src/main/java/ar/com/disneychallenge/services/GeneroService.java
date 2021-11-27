package ar.com.disneychallenge.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.disneychallenge.entities.Genero;
import ar.com.disneychallenge.repos.GeneroRepository;

@Service
public class GeneroService {

    @Autowired
    GeneroRepository repo;

    public boolean crearGenero(Genero genero) {
        
        if(existe(genero.getNombre()))
            return false;

        repo.save(genero);

        return true;
    }

    public List<Genero> traerGeneros() {

        return repo.findAll();
    }

    public Genero buscarGeneroPorId(Integer generoId) {
        
        Optional<Genero> resultado = repo.findById(generoId);
        Genero genero = null;

        if(resultado.isPresent())
            genero = resultado.get();
        
        return genero;
    }

    public boolean existePorId(int id) {

        Genero genero = repo.findById(id);
        return genero != null;
    }

    public boolean existe(String nombre) {

        Genero genero = repo.findByNombre(nombre);
        return genero != null;
    }

    public boolean eliminarGeneroPorId(Integer id) {

        boolean res = false;

        if(existePorId(id)) { 

            repo.deleteById(id);

            res = (!existePorId(id));
        }

        return res;  
    }
}
