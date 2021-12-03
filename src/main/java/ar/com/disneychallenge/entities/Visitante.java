package ar.com.disneychallenge.entities;

import javax.persistence.*;

@Entity
@Table(name = "visitante")
public class Visitante extends Persona {

    @Id
    @Column(name = "visitante_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer visitanteId;

    @OneToOne(mappedBy = "visitante", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Usuario usuario;

    public Integer getVisitanteId() {
        return visitanteId;
    }

    public void setVisitanteId(Integer visitanteId) {
        this.visitanteId = visitanteId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        usuario.setVisitante(this);
    }
}
