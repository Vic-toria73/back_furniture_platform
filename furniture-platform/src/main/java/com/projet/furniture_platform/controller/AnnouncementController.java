package com.projet.furniture_platform.controller;

import com.projet.furniture_platform.DTO.FurnitureDTO;
import com.projet.furniture_platform.entity.Furniture;
import com.projet.furniture_platform.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    // -----------------------------------------------------------
    // 🔹 POST → Création d’une annonce/meuble
    // -----------------------------------------------------------
    @PostMapping
    public ResponseEntity<Furniture> create(@Valid @RequestBody FurnitureDTO dto) {
        return ResponseEntity.ok(announcementService.create(dto));
    }

    // -----------------------------------------------------------
    // 🔹 GET → Liste des meubles disponibles
    // -----------------------------------------------------------
    @GetMapping("/available")
    public ResponseEntity<List<Furniture>> getAllAvailable() {
        return ResponseEntity.ok(announcementService.getAllAvailable());
    }

    // -----------------------------------------------------------
    // 🔹 GET → Meuble par ID (uniquement si disponible)
    // -----------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Furniture> getAvailableById(@PathVariable Integer id) {
        Furniture furniture = announcementService.getAvailableById(id);

        if (furniture == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(furniture);
    }
}
