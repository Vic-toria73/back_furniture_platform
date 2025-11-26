package com.projet.furniture_platform.DTO;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String firstname;
    private String lastname;
    private String email;
}
