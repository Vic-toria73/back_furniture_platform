package com.projet.furniture_platform.service;

import com.projet.furniture_platform.DTO.FurnitureUpdateRequest;
import com.projet.furniture_platform.entity.*;
import com.projet.furniture_platform.repository.*;

import lombok.RequiredArgsConstructor;
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

    public Furniture addFurniture(Furniture furniture) {
        return furnitureRepository.save(furniture);
    }

    public List<Furniture> getAll() {
        return furnitureRepository.findAllWithRelationsBy();
    }

    public Furniture getById(Integer id) {
        return furnitureRepository.findWithRelationsById(id).orElse(null);
    }

    // -----------------------------
    // UPDATE STATUS
    // -----------------------------
    public Furniture updateStatus(Integer id, Furniture.Status status) {
        Furniture furniture = furnitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Furniture not found"));
        furniture.setStatus(status);
        return furnitureRepository.save(furniture);
    }

    // UPDATE FURNITURE (NOUVEAU)
    public Furniture updateFurniture(Integer id, FurnitureUpdateRequest request) {
        Furniture furniture = furnitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Furniture not found"));

        if (request.getName() != null) {
            furniture.setName(request.getName());
        }
        if (request.getPrice() != null) {
            furniture.setPrice(request.getPrice());
        }
        if (request.getDescription() != null) {
            furniture.setDescription(request.getDescription());
        }
        if (request.getTypeId() != null) {
            Type type = typeRepository.findById(request.getTypeId())
                    .orElseThrow(() -> new RuntimeException("Type not found"));
            furniture.setType(type);
        }
        if (request.getColorId() != null) {
            Color color = colorRepository.findById(request.getColorId())
                    .orElseThrow(() -> new RuntimeException("Color not found"));
            furniture.setColor(color);
        }
        if (request.getMaterialId() != null) {
            Material material = materialRepository.findById(request.getMaterialId())
                    .orElseThrow(() -> new RuntimeException("Material not found"));
            furniture.setMaterial(material);
        }

        return furnitureRepository.save(furniture);
    }

    // UPDATE FURNITURE BY USER (ONLY OWNER)
    public Furniture updateFurnitureByUser(Integer id, Long userId, FurnitureUpdateRequest request) {
        Furniture furniture = furnitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Furniture not found"));

        // Vérifier si le meuble appartient au user
        if (!furniture.getUserId().equals(userId)) {
            throw new RuntimeException("You are not the owner of this furniture");
        }

        // Mise à jour autorisée pour l’utilisateur
        if (request.getName() != null) {
            furniture.setName(request.getName());
        }
        if (request.getPrice() != null) {
            furniture.setPrice(request.getPrice());
        }
        if (request.getDescription() != null) {
            furniture.setDescription(request.getDescription());
        }

        if (request.getTypeId() != null) {
            Type type = typeRepository.findById(request.getTypeId())
                    .orElseThrow(() -> new RuntimeException("Type not found"));
            furniture.setType(type);
        }

        if (request.getColorId() != null) {
            Color color = colorRepository.findById(request.getColorId())
                    .orElseThrow(() -> new RuntimeException("Color not found"));
            furniture.setColor(color);
        }

        if (request.getMaterialId() != null) {
            Material material = materialRepository.findById(request.getMaterialId())
                    .orElseThrow(() -> new RuntimeException("Material not found"));
            furniture.setMaterial(material);
        }

        if (furniture.getStatus() == Furniture.Status.REJECTED
                || furniture.getStatus() == Furniture.Status.VALIDATED) {
            furniture.setStatus(Furniture.Status.PENDING);
        }

        return furnitureRepository.save(furniture);
    }



    public List<Furniture> getPending() {
        return furnitureRepository.findByStatus(Furniture.Status.PENDING);
    }

    public List<Furniture> getByStatus(Furniture.Status status) {
        return furnitureRepository.findByStatus(status);
    }

    public List<Furniture> getPublicFurniture() {
        return furnitureRepository.findWithRelationsByStatusIn(List.of(
                Furniture.Status.VALIDATED,
                Furniture.Status.AVAILABLE
        ));
    }

    public List<Furniture> getUserFurniture(Long userId) {
        return furnitureRepository.findWithRelationsByUserId(userId);
    }

    // -----------------------------
    // DELETE (SOFT DELETE)
    // -----------------------------
    public void softDeleteFurniture(Integer id) {
        Furniture furniture = furnitureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Furniture not found"));
        furniture.setStatus(Furniture.Status.DELETED);
        furnitureRepository.save(furniture);
    }

    // -----------------------------
    // CREATION D'ANNONCE PAR USER
    // -----------------------------
    public Furniture createFurnitureFromUser(
            String name,
            Integer typeId,
            String description,
            String height,
            String width,
            String price,
            Long userId,
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