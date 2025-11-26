package com.projet.furniture_platform.service;

import com.projet.furniture_platform.DTO.PasswordRequest;
import com.projet.furniture_platform.DTO.UserUpdateRequest;
import com.projet.furniture_platform.entity.User;
import com.projet.furniture_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Liste complète
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtenir user par ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // Récupérer le user connecté
    public User getMe(Long userId) {
        return getUserById(userId);
    }

    //  Modifier les infos du user connecté
    public User updateMyAccount(Long userId, UserUpdateRequest request) {
        User user = getUserById(userId);

        if (request.getFirstname() != null) {
            user.setFirstName(request.getFirstname());
        }
        if (request.getLastname() != null) {
            user.setLastName(request.getLastname());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        return userRepository.save(user);
    }

    // Modifier le mot de passe
    public void updatePassword(Long userId, PasswordRequest request) {
        User user = getUserById(userId);

        // Vérifier ancien mdp
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        // Enregistrer le nouveau
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}