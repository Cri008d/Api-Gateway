package APi.gateway.Gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Desactivamos CSRF porque nuestra seguridad se basará en JWT, no en Cookies
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                // Dejamos la ruta de login 100% pública
                .requestMatchers("/auth/login").permitAll() 

                .requestMatchers("/api/dr/**").permitAll() 
                // Cualquier otra ruta extraña que intenten acceder, la bloqueamos
                .anyRequest().authenticated() 
            );
        
        return http.build();
    }
}
