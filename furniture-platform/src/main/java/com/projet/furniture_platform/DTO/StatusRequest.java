package com.projet.furniture_platform.DTO;

import com.projet.furniture_platform.entity.Furniture;
import lombok.Data;

@Data
public class StatusRequest {
    private Furniture.Status status;
}
