package com.projet.furniture_platform.controller;

import com.projet.furniture_platform.DTO.PasswordRequest;
import com.projet.furniture_platform.DTO.UserUpdateRequest;
import com.projet.furniture_platform.entity.User;
import com.projet.furniture_platform.security.CustomUserDetails;
import com.projet.furniture_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Récupérer le user connecté
    @GetMapping("/me")
    public ResponseEntity<User> getMyAccount() {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId = userDetails.getId();

        return ResponseEntity.ok(userService.getMe(userId));
    }

    // Modifier les infos du user connecté
    @PutMapping("/me")
    public ResponseEntity<User> updateMyAccount(@RequestBody UserUpdateRequest request) {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId = userDetails.getId();

        User updated = userService.updateMyAccount(userId, request);

        return ResponseEntity.ok(updated);
    }

    // Modifier le mot de passe du user connecté
    @PutMapping("/me/password")
    public ResponseEntity<?> updateMyPassword(@RequestBody PasswordRequest request) {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId = userDetails.getId();

        userService.updatePassword(userId, request);

        return ResponseEntity.ok("Password updated successfully");
    }
}