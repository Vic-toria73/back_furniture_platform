package com.projet.furniture_platform.service;

import com.projet.furniture_platform.DTO.FurnitureDTO;
import com.projet.furniture_platform.entity.Furniture;
import com.projet.furniture_platform.repository.FurnitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final FurnitureRepository furnitureRepository;

    public AnnouncementService(FurnitureRepository furnitureRepository) {
        this.furnitureRepository = furnitureRepository;
    }

    // Convertir Entity -> DTO
    private FurnitureDTO toDTO(Furniture furniture) {
        return FurnitureDTO.builder()
                .id(furniture.getId())
                .name(furniture.getName())
                .typeId(furniture.getTypeId())
                .description(furniture.getDescription())
                .height(furniture.getHeight())
                .width(furniture.getWidth())
                .price(furniture.getPrice())
                .status(furniture.getStatus())
                .orderId(furniture.getOrderId())
                .addressId(furniture.getAddressId())
                .userId(furniture.getUserId())
                .build();
    }

    // Convertir DTO -> Entity
    private Furniture toEntity(FurnitureDTO dto) {
        Furniture furniture = new Furniture();
        furniture.setName(dto.getName());
        furniture.setTypeId(dto.getTypeId());
        furniture.setDescription(dto.getDescription());
        furniture.setHeight(dto.getHeight());
        furniture.setWidth(dto.getWidth());
        furniture.setPrice(dto.getPrice());
        furniture.setStatus(dto.getStatus() != null ? dto.getStatus() : Furniture.FurnitureStatus.VALIDATED);
        furniture.setOrderId(dto.getOrderId());
        furniture.setAddressId(dto.getAddressId());
        furniture.setUserId(dto.getUserId());
        return furniture;
    }

    public List<FurnitureDTO> getAllAnnouncements() {
        return furnitureRepository.findByStatus(Furniture.FurnitureStatus.VALIDATED)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<FurnitureDTO> getAnnouncementById(Integer id) {
        return furnitureRepository.findByIdAndStatus(id, Furniture.FurnitureStatus.VALIDATED)
                .map(this::toDTO);
    }

    public FurnitureDTO saveAnnouncement(FurnitureDTO dto) {
        Furniture furniture = toEntity(dto);
        Furniture saved = furnitureRepository.save(furniture);
        return toDTO(saved);
    }

    public Optional<FurnitureDTO> updateAnnouncement(Integer id, FurnitureDTO dto) {
        return furnitureRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setTypeId(dto.getTypeId());
            existing.setDescription(dto.getDescription());
            existing.setHeight(dto.getHeight());
            existing.setWidth(dto.getWidth());
            existing.setPrice(dto.getPrice());
            existing.setStatus(dto.getStatus() != null ? dto.getStatus() : existing.getStatus());
            existing.setOrderId(dto.getOrderId());
            existing.setAddressId(dto.getAddressId());
            existing.setUserId(dto.getUserId());
            return toDTO(furnitureRepository.save(existing));
        });
    }

    public boolean deleteAnnouncement(Integer id) {
        return furnitureRepository.findById(id).map(f -> {
            furnitureRepository.delete(f);
            return true;
        }).orElse(false);
    }
}

