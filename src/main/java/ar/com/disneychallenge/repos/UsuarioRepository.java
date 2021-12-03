package ar.com.disneychallenge.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.disneychallenge.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByUsername(String userName);
    public Usuario findByEmail(String email);
}
