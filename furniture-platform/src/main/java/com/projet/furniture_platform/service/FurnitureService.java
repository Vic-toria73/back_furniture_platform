package com.projet.furniture_platform.service;

import com.projet.furniture_platform.entity.*;
import com.projet.furniture_platform.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FurnitureService {

    private final FurnitureRepository furnitureRepository;
    private final TypeRepository typeRepository;
    private final PictureRepository pictureRepository;
    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;
    private final UserRepository userRepository; // <-- AJOUT ICI

    public Furniture addFurniture(Furniture furniture) {
        return furnitureRepository.save(furniture);
    }

    public List<Furniture> getAll() {
        return furnitureRepository.findAll();
    }

    public Furniture getById(Integer id) {
        return furnitureRepository.findById(id).orElse(null);
    }

    // -----------------------------
    // UPDATE STATUS avec changement user_id
    // -----------------------------
    public Furniture updateStatus(Integer id, Furniture.Status status) {

        Furniture furniture = furnitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Furniture not found"));

        // Mise à jour du statut
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


    // -----------------------------
    // 🔥 CREATION D’ANNONCE PAR USER
    // -----------------------------
    public Furniture createFurnitureFromUser(
            String name,
            Integer typeId,
            String description,
            String height,
            String width,
            String price,
            Integer userId,
            Integer colorId,
            Integer materialId,
            List<MultipartFile> photos
    ) {
        try {
            Type type = typeRepository.findById(typeId)
                    .orElseThrow(() -> new RuntimeException("Type not found"));

            Color color = colorRepository.findById(colorId)
                    .orElseThrow(() -> new RuntimeException("Color not found"));

            Material material = materialRepository.findById(materialId)
                    .orElseThrow(() -> new RuntimeException("Material not found"));

            // Création du meuble
            Furniture furniture = new Furniture();
            furniture.setName(name);
            furniture.setType(type);
            furniture.setDescription(description);
            furniture.setHeight(new BigDecimal(height));
            furniture.setWidth(new BigDecimal(width));
            furniture.setPrice(new BigDecimal(price));
            furniture.setUserId(userId);
            furniture.setColor(color);
            furniture.setMaterial(material);
            furniture.setStatus(Furniture.Status.PENDING);

            furniture = furnitureRepository.save(furniture);

            Path uploadDir = Paths.get("uploads");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            for (MultipartFile file : photos) {
                String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadDir.resolve(filename);

                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                Picture picture = new Picture();
                picture.setUrl(filename);
                picture.setFurniture(furniture);

                pictureRepository.save(picture);
            }

            return furniture;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating furniture: " + e.getMessage());
        }
    }

}
