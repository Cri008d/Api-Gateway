package APi.gateway.Gateway.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import APi.gateway.Gateway.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring Boot crea la consulta SQL automáticamente solo con leer el nombre de este método
    Optional<Usuario> findByEmail(String email);
}
