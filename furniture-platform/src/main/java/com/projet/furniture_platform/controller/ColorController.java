package com.projet.furniture_platform.controller;

import com.projet.furniture_platform.entity.Color;
import com.projet.furniture_platform.repository.ColorRepository;
import com.projet.furniture_platform.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/color")
public class ColorController {

    @Autowired
    private ColorRepository colorRepository;

    @GetMapping("/all")
    public List<Color> getAll() {
        return colorRepository.findAll();
    }
}

