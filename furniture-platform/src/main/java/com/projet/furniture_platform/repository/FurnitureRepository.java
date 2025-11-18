package com.projet.furniture_platform.repository;

import com.projet.furniture_platform.DTO.FurnitureDTO;
import com.projet.furniture_platform.entity.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FurnitureRepository extends JpaRepository<Furniture, Integer> {

    List<Furniture> findByStatus(Furniture.Status status);

    Optional<Furniture> findByIdAndStatus(Integer id, Furniture.Status status);

    List<Furniture> findByStatusIn(List<Furniture.Status> statuses);

}
