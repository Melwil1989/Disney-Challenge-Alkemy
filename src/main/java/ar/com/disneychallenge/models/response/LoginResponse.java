package ar.com.disneychallenge.models.response;

import ar.com.disneychallenge.entities.Usuario.TipoUsuarioEnum;

public class LoginResponse {

    public Integer id;
    public String username;
    public String token;
    public String email;
    public TipoUsuarioEnum userType;
    public Integer entityId;
}
