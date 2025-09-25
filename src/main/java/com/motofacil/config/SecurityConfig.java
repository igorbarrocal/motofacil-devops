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
                .requestMatchers("/api/auth/**").permitAll() // Permitir acesso público às rotas de autenticação temporalmente
                .requestMatchers("/api/user/**").permitAll() // Permitir acesso público às rotas de autenticação e usuário temporalmente
                .requestMatchers("/api/location/**").permitAll() // Permitir acesso público às rotas de localização temporalmente
                .requestMatchers("/api/patios/**").permitAll() // Permitir acesso público às rotas de pátios temporalmente
                .requestMatchers("/api/motos/**").permitAll() // Permitir acesso público às rotas de motos temporalmente
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
