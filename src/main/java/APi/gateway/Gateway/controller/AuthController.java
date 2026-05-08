package APi.gateway.Gateway.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import APi.gateway.Gateway.model.LoginRequestDTO;
import APi.gateway.Gateway.model.Usuario;
import APi.gateway.Gateway.repository.UsuarioRepository;
import APi.gateway.Gateway.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        // Buscamos al usuario por su email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());

        // Verificamos si existe y si la contraseña es correcta
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            if (usuario.getPassword().equals(request.getPassword())) {
                // Generamos el token 
                String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol());
                
                // Se lo enviamos al Frontend en formato JSON
                Map<String, String> respuesta = new HashMap<>();
                respuesta.put("token", token);
                return ResponseEntity.ok(respuesta);
            }
        }
        
        // Si la contraseña o el correo están mal, devolvemos un error 401 Unauthorized
        Map<String, String> error = new HashMap<>();
        error.put("mensaje", "Credenciales inválidas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
