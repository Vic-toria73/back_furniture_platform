package com.projet.furniture_platform.controller;

import com.projet.furniture_platform.DTO.LoginRequest;
import com.projet.furniture_platform.DTO.RegisterRequest;
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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest req) {

        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(req.email())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cet email est déjà utilisé");
        }

        // Construire et sauvegarder l'utilisateur
        User user = new User();
        user.setEmail(req.email());
        user.setFirstName(req.firstname());
        user.setLastName(req.lastname());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return ResponseEntity.ok("Utilisateur créé !");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userRepository.findByEmail(request.email()).orElseThrow();

            String token = jwtUtils.generateToken(userDetails);

            Map<String, Object> authData = new HashMap<>();
            authData.put("token", token);
            authData.put("type", "Bearer");
            authData.put("role", user.getRole());
            authData.put("id", user.getId());

            return ResponseEntity.ok(authData);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of(
                            "error", "Authentication failed",
                            "message", "Identifiants incorrects",
                            "exception", ex.getClass().getSimpleName()
                    )
            );
        }
    }

}