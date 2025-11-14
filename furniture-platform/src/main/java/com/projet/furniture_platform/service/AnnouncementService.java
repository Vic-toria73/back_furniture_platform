package com.projet.furniture_platform.service;

import com.projet.furniture_platform.DTO.FurnitureDTO;
import com.projet.furniture_platform.entity.Furniture;
import com.projet.furniture_platform.repository.FurnitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final FurnitureRepository furnitureRepository;

    // -----------------------------------------------------------
    // 🔹 CREATE / POST → Créer une annonce (= un meuble)
    // -----------------------------------------------------------
    public Furniture create(FurnitureDTO dto) {

        Furniture furniture = new Furniture();
        furniture.setName(dto.getName());
        furniture.setTypeId(dto.getTypeId());
        furniture.setDescription(dto.getDescription());
        furniture.setHeight(dto.getHeight());
        furniture.setWidth(dto.getWidth());
        furniture.setPrice(dto.getPrice());
        furniture.setOrderId(dto.getOrderId());
        furniture.setAddressId(dto.getAddressId());
        furniture.setUserId(dto.getUserId());

        // Status par défaut
        furniture.setStatus(
                dto.getStatus() != null ?
                        dto.getStatus() :
                        Furniture.Status.AVAILABLE
        );

        return furnitureRepository.save(furniture);
    }

    // -----------------------------------------------------------
    // 🔹 GET → Retourne toutes les annonces validées
    // -----------------------------------------------------------
    public List<Furniture> getAllAvailable() {
        return furnitureRepository.findByStatus(Furniture.Status.AVAILABLE);
    }

    // -----------------------------------------------------------
    // 🔹 GET → Retourne un meuble par ID S’IL EST DISPONIBLE
    // -----------------------------------------------------------
    public Furniture getAvailableById(Integer id) {
        return furnitureRepository.findByIdAndStatus(id, Furniture.Status.AVAILABLE)
                .orElse(null);
    }
}
