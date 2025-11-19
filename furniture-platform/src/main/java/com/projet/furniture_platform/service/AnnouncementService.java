package com.projet.furniture_platform.service;

import com.projet.furniture_platform.DTO.FurnitureDTO;
import com.projet.furniture_platform.entity.Furniture;
import com.projet.furniture_platform.entity.Type;
import com.projet.furniture_platform.repository.ColorRepository;
import com.projet.furniture_platform.repository.FurnitureRepository;
import com.projet.furniture_platform.repository.MaterialRepository;
import com.projet.furniture_platform.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final FurnitureRepository furnitureRepository;
    private final TypeRepository typeRepository;
    private final ColorRepository colorRepository;
    private final MaterialRepository materialRepository;

    public Furniture create(FurnitureDTO dto) {

        Furniture furniture = new Furniture();
        furniture.setName(dto.getName());

        // Type obligatoire
        Type type = typeRepository.findById(dto.getTypeId())
                .orElseThrow(() -> new RuntimeException("Type not found"));
        furniture.setType(type);

        // Couleur (optionnelle)
        if (dto.getColorId() != null) {
            furniture.setColor(
                    colorRepository.findById(dto.getColorId())
                            .orElseThrow(() -> new RuntimeException("Color not found"))
            );
        }

        // Matériau (optionnel)
        if (dto.getMaterialId() != null) {
            furniture.setMaterial(
                    materialRepository.findById(dto.getMaterialId())
                            .orElseThrow(() -> new RuntimeException("Material not found"))
            );
        }

        furniture.setDescription(dto.getDescription());
        furniture.setHeight(dto.getHeight());
        furniture.setWidth(dto.getWidth());
        furniture.setPrice(dto.getPrice());

        // OrderId → il n'existe qu'en cas d'achat !
        furniture.setOrder(null);

        // Adresse : à ne gérer que si tu as une table address
        furniture.setAddressId(null);

        furniture.setUserId(dto.getUserId());

        // Statut par défaut
        furniture.setStatus(Furniture.Status.AVAILABLE);

        return furnitureRepository.save(furniture);
    }

    public List<Furniture> getAllAvailable() {
        return furnitureRepository.findByStatus(Furniture.Status.AVAILABLE);
    }

    public Furniture getAvailableById(Integer id) {
        return furnitureRepository.findByIdAndStatus(id, Furniture.Status.AVAILABLE)
                .orElse(null);
    }
}
