package com.graduation.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // We disable this because we are building a REST API
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Opens up our login and signup URLs
                        .requestMatchers("/", "/index.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll() // Allows the root URL and Tala's frontend files to load
                        .anyRequest().authenticated() // Everything else is locked down!
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // This is the tool that hashes the passwords before saving them
        return new BCryptPasswordEncoder();
    }
}