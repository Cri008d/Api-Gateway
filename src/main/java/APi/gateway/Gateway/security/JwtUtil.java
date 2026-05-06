package APi.gateway.Gateway.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Genera una llave secreta segura para firmar los tokens
    private final Key llaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    // El token durará 1 día (en milisegundos)
    private final long TIEMPO_EXPIRACION = 2592000000L; 

    public String generarToken(String email, String rol) {
        return Jwts.builder()
                .setSubject(email)
                .claim("rol", rol) // Guardamos el rol dentro del token
                .setIssuedAt(new Date()) // Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + TIEMPO_EXPIRACION)) // Cuándo caduca
                .signWith(llaveSecreta) // Lo firmamos para que nadie pueda falsificarlo
                .compact();
    }
}
