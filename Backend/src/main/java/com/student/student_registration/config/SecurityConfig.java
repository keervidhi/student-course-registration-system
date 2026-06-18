package com.student.student_registration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Development-friendly: disable CSRF and allow all endpoints
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()   // allow all API requests
                        .anyRequest().permitAll()                 // allow any other request (remove or change for prod)
                )
                // no login page or basic auth needed in this dev config
                .httpBasic(Customizer.withDefaults()) // optional: allow basic auth if called by clients
                .formLogin(Customizer.withDefaults()); // optional (doesn't show default form if endpoints are permitted)

        return http.build();
    }
}

