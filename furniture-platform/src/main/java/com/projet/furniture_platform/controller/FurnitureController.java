package com.projet.furniture_platform.controller;

import com.projet.furniture_platform.entity.Furniture;
import com.projet.furniture_platform.service.FurnitureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/furniture")
@RequiredArgsConstructor
public class FurnitureController {

    private final FurnitureService furnitureService;

    @PostMapping
    public Furniture createFurniture(@RequestBody Furniture furniture) {
        return furnitureService.addFurniture(furniture);
    }

    @GetMapping
    public List<Furniture> getFurnitureList() {
        return furnitureService.getAll();
    }
}
