package com.motofacil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Permitir acesso público às rotas de autenticação, usuário, localização, pátios, motos
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/user/**").permitAll()
                .requestMatchers("/api/location/**").permitAll()
                .requestMatchers("/api/patios/**").permitAll()
                .requestMatchers("/api/motos/**").permitAll()

                // Permitir acesso público ao Swagger e à raiz do app
                .requestMatchers("/", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}