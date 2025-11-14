package com.projet.furniture_platform.controller;

import com.projet.furniture_platform.entity.Furniture;
import com.projet.furniture_platform.entity.Picture;
import com.projet.furniture_platform.repository.FurnitureRepository;
import com.projet.furniture_platform.repository.PictureRepository;
import com.projet.furniture_platform.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pictures")
@RequiredArgsConstructor
public class PictureController {

    private final PictureRepository pictureRepository;
    private final FurnitureRepository furnitureRepository;

    @PostMapping("/furniture/{id}")
    public ResponseEntity<Picture> addPicture(
            @PathVariable Integer id,
            @RequestBody Picture pictureData
    ) {

        Furniture furniture = furnitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Furniture not found"));

        Picture picture = new Picture();
        picture.setUrl(pictureData.getUrl());
        picture.setAltText(pictureData.getAltText());
        picture.setFurniture(furniture);

        return ResponseEntity.ok(pictureRepository.save(picture));
    }

    @GetMapping("/furniture/{id}")
    public List<Picture> getPicturesByFurniture(@PathVariable Integer id) {
        return pictureRepository.findByFurnitureId(id);
    }
}
