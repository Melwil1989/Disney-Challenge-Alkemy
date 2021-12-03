package ar.com.disneychallenge.entities;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @Column(name = "usuario_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer usuarioId;

    @NaturalId
    private String username;

    private String password;

    private String email;

    @Column(name = "fecha_login")
    private Date fechaLogin;

    @Column(name = "tipo_usuario_id")
    private Integer tipoUsuarioId;

    @OneToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
    private Staff staff;

    @OneToOne
    @JoinColumn(name = "visitante_id", referencedColumnName = "visitante_id")
    private Visitante visitante;

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaLogin() {
        return fechaLogin;
    }

    public void setFechaLogin(Date fechaLogin) {
        this.fechaLogin = fechaLogin;
    }

    public TipoUsuarioEnum getTipoUsuarioId() {
        return TipoUsuarioEnum.parse(this.tipoUsuarioId);
    }

    public void setTipoUsuarioId(TipoUsuarioEnum tipoUsuarioId) {
        this.tipoUsuarioId = tipoUsuarioId.getValue();
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Visitante getVisitante() {
        return visitante;
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }

    public Integer obtenerEntityId() {

        switch (this.getTipoUsuarioId()) {
            case VISITANTE:
                 return this.getVisitante().getVisitanteId();
            case STAFF:
                return this.getStaff().getStaffId();
            default:
                break;
        }
        return null;
    }

    public enum TipoUsuarioEnum {

        STAFF(1), VISITANTE(2);

        private final Integer value;

        private TipoUsuarioEnum(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        public static TipoUsuarioEnum parse(Integer id) {
            TipoUsuarioEnum status = null; // Default
            for (TipoUsuarioEnum item : TipoUsuarioEnum.values()) {
                if (item.getValue().equals(id)) {
                    status = item;
                    break;
                }
            }
            return status;
        }
    }
}
