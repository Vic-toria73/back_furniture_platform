package com.projet.furniture_platform.controller;

import com.projet.furniture_platform.DTO.FurnitureDTO;
import com.projet.furniture_platform.service.AnnouncementService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    // GET -> toutes les annonces validées
    @GetMapping
    public ResponseEntity<List<FurnitureDTO>> getAllAnnouncements() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }

    // GET -> détail d'une annonce
    @GetMapping("/{id}")
    public ResponseEntity<FurnitureDTO> getAnnouncement(@PathVariable Integer id) {
        return announcementService.getAnnouncementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ---------------- ADMIN ONLY ----------------
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FurnitureDTO> createAnnouncement(@Valid @RequestBody FurnitureDTO dto) {
        return ResponseEntity.ok(announcementService.saveAnnouncement(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FurnitureDTO> updateAnnouncement(@PathVariable Integer id,
                                                           @Valid @RequestBody FurnitureDTO dto) {
        return announcementService.updateAnnouncement(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Integer id) {
        if (announcementService.deleteAnnouncement(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}