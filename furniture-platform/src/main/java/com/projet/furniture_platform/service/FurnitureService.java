package com.projet.furniture_platform.service;

import com.projet.furniture_platform.entity.Furniture;
import com.projet.furniture_platform.repository.FurnitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FurnitureService {

    private final FurnitureRepository furnitureRepository;

    public Furniture addFurniture(Furniture furniture) {
        return furnitureRepository.save(furniture);
    }

    public List<Furniture> getAll() {
        return furnitureRepository.findAll();
    }

    public Furniture getById(Integer id) {
        return furnitureRepository.findById(id)
                .orElse(null);
    }

    public Furniture updateStatus(Integer id, Furniture.Status status) {
        Furniture furniture = furnitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Furniture not found"));

        furniture.setStatus(status);
        return furnitureRepository.save(furniture);
    }

    public List<Furniture> getPending() {
        return furnitureRepository.findByStatus(Furniture.Status.PENDING);
    }

    public List<Furniture> getByStatus(Furniture.Status status) {
        return furnitureRepository.findByStatus(status);
    }

    public List<Furniture> getPublicFurniture() {
        return furnitureRepository.findByStatusIn(List.of(
                Furniture.Status.VALIDATED,
                Furniture.Status.AVAILABLE
        ));
    }
}
