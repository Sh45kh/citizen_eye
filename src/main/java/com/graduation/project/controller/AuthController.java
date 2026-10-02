package com.graduation.project.controller;

import com.graduation.project.model.User;
import com.graduation.project.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController // Tells Spring this is an API controller
@RequestMapping("/api/auth") // The base URL for everything in this file
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- SIGNUP ENDPOINT ---
    // URL: http://localhost:8080/api/auth/signup
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User newUser) {

        // 1. Check if the email is already taken
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // 2. Hash the password so it is secure in PostgreSQL
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        // 3. Save the new user to the database
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully!");
    }

    // --- LOGIN ENDPOINT ---
    // URL: http://localhost:8080/api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        // 1. Find the user by their email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isPresent()) {
            User databaseUser = userOptional.get();

            // 2. Check if the typed password matches the hashed password in the DB
            if (passwordEncoder.matches(loginRequest.getPassword(), databaseUser.getPassword())) {
                return ResponseEntity.ok("Login successful! Welcome, " + databaseUser.getFullName());
            }
        }

        // 3. If email isn't found OR password doesn't match
        return ResponseEntity.badRequest().body("Error: Invalid email or password.");
    }

    // --- HELPER CLASS ---
    // This catches the exact JSON sent from Tala's frontend during login
    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }
}