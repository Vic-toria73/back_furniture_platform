package com.projet.furniture_platform.controller;

import com.projet.furniture_platform.DTO.StatusRequest;
import com.projet.furniture_platform.entity.Furniture;
import com.projet.furniture_platform.service.FurnitureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/furniture")
@RequiredArgsConstructor
public class FurnitureController {

    private final FurnitureService furnitureService;


    // ------------------ PUBLIC ------------------

    // Tous les meubles visibles (pour le site public)
    @GetMapping("/public")
    public List<Furniture> getPublicFurniture() {
        return furnitureService.getPublicFurniture();
    }

    // Détails d’un meuble public
    @GetMapping("/public/{id}")
    public ResponseEntity<Furniture> getFurniturePublic(@PathVariable Integer id) {
        Furniture furniture = furnitureService.getById(id);
        if (furniture == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(furniture);
    }


    // ------------------ ADMIN ------------------

    // Ajouter un meuble
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Furniture createFurniture(@RequestBody Furniture furniture) {
        return furnitureService.addFurniture(furniture);
    }

    // Liste complète (tous statuts)
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Furniture> getFurnitureList() {
        return furnitureService.getAll();
    }

    // Obtenir un meuble (admin)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Furniture> getFurnitureById(@PathVariable Integer id) {
        Furniture furniture = furnitureService.getById(id);
        if (furniture == null) { return ResponseEntity.notFound().build(); }
        return ResponseEntity.ok(furniture);
    }

    // Modifier statut
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Furniture> updateStatus(
            @PathVariable Integer id,
            @RequestBody StatusRequest request
    ) {
        Furniture updated = furnitureService.updateStatus(id, request.getStatus());
        return ResponseEntity.ok(updated);
    }

    // Filtrer par statut
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Furniture>> getFurnitureByStatus(@PathVariable String status) {
        try {
            Furniture.Status enumStatus = Furniture.Status.valueOf(status.toUpperCase());
            List<Furniture> list = furnitureService.getByStatus(enumStatus);
            return ResponseEntity.ok(list);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ResponseEntity<?> createFurnitureWithImages(
            @RequestParam("name") String name,
            @RequestParam("typeId") Integer typeId,
            @RequestParam("description") String description,
            @RequestParam("height") String height,
            @RequestParam("width") String width,
            @RequestParam("price") String price,
            @RequestParam("userId") Integer userId,
            @RequestParam("colorId") Integer colorId,
            @RequestParam("materialId") Integer materialId,
            @RequestPart("photos") List<MultipartFile> photos
    ) {
        Furniture furniture = furnitureService.createFurnitureFromUser(
                name, typeId, description, height, width, price, userId, colorId, materialId, photos
        );
        return ResponseEntity.ok(furniture);
    }

}
