package ar.com.disneychallenge.entities;

import javax.persistence.*;

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
}
