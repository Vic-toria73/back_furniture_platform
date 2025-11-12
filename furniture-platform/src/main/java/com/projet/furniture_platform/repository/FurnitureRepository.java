package com.projet.furniture_platform.repository;

import com.projet.furniture_platform.entity.Furniture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Integer> {

    List<Furniture> findByStatus(Furniture.FurnitureStatus status);

    Optional<Furniture> findByIdAndStatus(Integer id, Furniture.FurnitureStatus status);
}
