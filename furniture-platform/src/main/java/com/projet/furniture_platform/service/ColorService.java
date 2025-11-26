package com.projet.furniture_platform.service;

import com.projet.furniture_platform.entity.Color;
import com.projet.furniture_platform.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {

    private final ColorRepository colorRepository;

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    public Color getById(Integer id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Color not found"));
    }
}
