package com.projet.furniture_platform.controller;

import com.projet.furniture_platform.entity.Material;
import com.projet.furniture_platform.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private MaterialRepository materialRepository;

    @GetMapping("/all")
    public List<Material> getAll() {
        return materialRepository.findAll();
    }
}

