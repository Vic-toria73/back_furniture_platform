package com.projet.furniture_platform.service;

import com.projet.furniture_platform.entity.Furniture;
import com.projet.furniture_platform.entity.Picture;
import com.projet.furniture_platform.repository.FurnitureRepository;
import com.projet.furniture_platform.repository.PictureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PictureService {

    private final PictureRepository pictureRepository;
    private final FurnitureRepository furnitureRepository;

    public List<Picture> getPicturesForFurniture(Integer furnitureId) {
        return pictureRepository.findByFurnitureId(furnitureId);
    }

    public Picture addPictureToFurniture(Integer furnitureId, Picture picture) {

        Furniture furniture = furnitureRepository.findById(furnitureId)
                .orElseThrow(() -> new RuntimeException("Furniture not found"));

        picture.setFurniture(furniture);

        return pictureRepository.save(picture);
    }
}

