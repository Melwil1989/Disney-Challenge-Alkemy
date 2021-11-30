package ar.com.disneychallenge.entities;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @Column(name = "pelicula_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer peliculaId;

    @Column(name = "imagen_pelicula")
    private String imagenPelicula;

    private String titulo;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    private Integer calificacion;

    @ManyToOne
    @JoinColumn(name = "genero_id", referencedColumnName = "genero_id")
    @JsonIgnore
    private Genero genero;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Personaje> personajes = new ArrayList<>();

    public Integer getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(Integer peliculaId) {
        this.peliculaId = peliculaId;
    }

    public String getImagenPelicula() {
        return imagenPelicula;
    }

    public void setImagenPelicula(String imagenPelicula) {
        this.imagenPelicula = imagenPelicula;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
        this.genero.agregarPelicula(this);
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    public void agregarPersonaje(Personaje personaje) {
        this.personajes.add(personaje);
    }
}
