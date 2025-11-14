package com.projet.furniture_platform.repository;

import com.projet.furniture_platform.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {

    // Récupère toutes les photos liées à un meuble
    List<Picture> findByFurnitureId(Integer furnitureId);
}