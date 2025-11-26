package com.projet.furniture_platform.repository;

import com.projet.furniture_platform.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
}
