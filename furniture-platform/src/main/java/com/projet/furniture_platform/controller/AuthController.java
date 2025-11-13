package com.projet.furniture_platform.controller;

import com.projet.furniture_platform.configuration.JwtUtils;
import com.projet.furniture_platform.entity.Role;
import com.projet.furniture_platform.entity.User;
import com.projet.furniture_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @GetMapping("/test")
    public String test() {
        return "AuthController OK";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                Map<String, Object> authData = new HashMap<>();
                authData.put("token", jwtUtils.generateToken(userDetails));
                authData.put("type", "Bearer");

                return ResponseEntity.ok(authData);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Authentication failed",
                            "message", ex.getMessage(),
                            "exception", ex.getClass().getName()
                    ));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Vérifie si un utilisateur existe déjà avec cet email
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Cet email est déjà utilisé"));
            }

            // Encode le mot de passe
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Définit un rôle par défaut si non fourni
            if (user.getRole() == null) {
                user.setRole(Role.USER);
            }

            User savedUser = userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Utilisateur créé avec succès",
                            "email", savedUser.getEmail()
                    ));

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Erreur lors de la création de l'utilisateur",
                            "message", ex.getMessage()
                    ));
        }
    }
}
