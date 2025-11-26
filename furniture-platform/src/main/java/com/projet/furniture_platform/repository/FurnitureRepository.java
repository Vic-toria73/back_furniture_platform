package com.projet.furniture_platform.repository;

import com.projet.furniture_platform.entity.Furniture;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FurnitureRepository extends JpaRepository<Furniture, Integer> {

    List<Furniture> findByStatus(Furniture.Status status);

    Optional<Furniture> findByIdAndStatus(Integer id, Furniture.Status status);

    List<Furniture> findByStatusIn(List<Furniture.Status> statuses);

    @EntityGraph(attributePaths = {"type", "color", "material", "pictures"})
    List<Furniture> findWithRelationsByUserId(Long userId);

    @EntityGraph(attributePaths = {"type", "color", "material", "pictures"})
    Optional<Furniture> findWithRelationsById(Integer id);

    @EntityGraph(attributePaths = {"type", "color", "material", "pictures"})
    List<Furniture> findWithRelationsByStatusIn(List<Furniture.Status> statuses);

    @EntityGraph(attributePaths = {"type", "color", "material", "pictures"})
    List<Furniture> findAllWithRelationsBy();
}