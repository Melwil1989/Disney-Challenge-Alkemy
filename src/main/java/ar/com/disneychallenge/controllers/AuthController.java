package ar.com.disneychallenge.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ar.com.disneychallenge.entities.Usuario;
import ar.com.disneychallenge.models.request.*;
import ar.com.disneychallenge.models.response.*;
import ar.com.disneychallenge.security.jwt.JWTTokenUtil;
import ar.com.disneychallenge.services.JWTUserDetailsService;
import ar.com.disneychallenge.services.UsuarioService;

@RestController
public class AuthController {

    @Autowired
    UsuarioService usuarioService;

    /*
    * @Autowired private AuthenticationManager authenticationManager;
    */

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    // Auth: authentication ->
    @PostMapping("/auth/register")
    public ResponseEntity<RegistrationResponse> postRegisterUser(@RequestBody RegistrationRequest req,
            BindingResult results) {

        RegistrationResponse r = new RegistrationResponse();

        // aca creamos la persona y el usuarion a traves del service

        Usuario usuario = usuarioService.crearUsuario(req.userType, req.fullName, req.country, req.birthDate,
                req.identificationType, req.identification, req.email, req.password);

        r.isOk = true;
        r.message = "Te registraste con exito!!!!!!";
        r.userId = usuario.getUsuarioId(); // <- aqui ponemos el numero de ID para darle a front

        return ResponseEntity.ok(r);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest,
            BindingResult results) throws Exception {

        Usuario usuarioLogueado = usuarioService.login(authenticationRequest.username, authenticationRequest.password);

        UserDetails userDetails = usuarioService.getUserAsUserDetail(usuarioLogueado);
        Map<String, Object> claims = usuarioService.getUserClaims(usuarioLogueado);

        // Genero los roles pero con los Claims (los propositos)
        // En este caso nuestros claims tienen info del tipo de usuario
        // y de la entidad que representa
        // Esta info va a viajar con el token, o sea, cualquiera puede
        // ver esos ids de que user pertenecen si logran interceptar el token
        // Por eso es que en cada request debemos validar el token (firma)

        String token = jwtTokenUtil.generateToken(userDetails, claims);

        // Cambio para que devuelva el full perfil

        Usuario u = usuarioService.buscarPorUsername(authenticationRequest.username);

        LoginResponse r = new LoginResponse();
        r.id = u.getUsuarioId();
        r.userType = u.getTipoUsuarioId();
        r.entityId = u.obtenerEntityId();
        r.username = authenticationRequest.username;
        r.email = u.getEmail();
        r.token = token;

        return ResponseEntity.ok(r);
    }
}
