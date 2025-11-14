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
}

