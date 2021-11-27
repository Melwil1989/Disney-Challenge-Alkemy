package ar.com.disneychallenge.models.response;

import java.util.*;

import ar.com.disneychallenge.entities.Personaje;

public class InfoPeliResponse {

    public String imagenPelicula;
    public String titulo;
    public Date fechaCreacion;
    public Integer calificacion;
    public Integer generoId;
    public List<Personaje> personajes;
}
