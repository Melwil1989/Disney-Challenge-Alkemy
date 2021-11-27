package ar.com.disneychallenge.entities;

import javax.persistence.*;

@Entity
@Table(name = "personaje")
public class Personaje {

    @Id
    @Column(name = "personaje_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personajeId;

    private String imagen;

    private String nombre;

    private Integer edad;

    private Integer peso;

    private String historia;

    @ManyToOne
    @JoinColumn(name = "pelicula_id", referencedColumnName = "pelicula_id")
    private Pelicula pelicula;

    public Integer getPersonajeId() {
        return personajeId;
    }

    public void setPersonajeId(Integer personajeId) {
        this.personajeId = personajeId;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
        this.pelicula.agregarPersonaje(this);
    }
}
