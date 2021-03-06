package ar.com.disneychallenge.entities;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "genero")
public class Genero {

    @Id
    @Column(name = "genero_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer generoId;

    @Column(name = "imagen_genero")
    private String imagenGenero;

    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Pelicula> peliculas = new ArrayList<>();

    public Integer getGeneroId() {
        return generoId;
    }

    public void setGeneroId(Integer generoId) {
        this.generoId = generoId;
    }

    public String getImagenGenero() {
        return imagenGenero;
    }

    public void setImagenGenero(String imagenGenero) {
        this.imagenGenero = imagenGenero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public void agregarPelicula(Pelicula pelicula) {
        this.peliculas.add(pelicula);
    }
}
