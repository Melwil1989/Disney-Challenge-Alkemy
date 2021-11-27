package ar.com.disneychallenge.entities;

import java.util.Date;

import javax.persistence.*;

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
}
