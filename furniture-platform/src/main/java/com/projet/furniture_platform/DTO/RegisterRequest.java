package com.projet.furniture_platform.DTO;

public record RegisterRequest(
        String email,
        String firstname,
        String lastname,
        String password
) {}