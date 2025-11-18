package com.projet.furniture_platform.controller;


import com.projet.furniture_platform.entity.Type;
import com.projet.furniture_platform.service.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/types")
public class TypeController {


    private final TypeService typeService;


    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }


    @GetMapping
    public List<Type> getAllTypes() {
        return typeService.getAllTypes();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Type> getTypeById(@PathVariable Integer id) {
        return typeService.getTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Type createType(@RequestBody Type type) {
        return typeService.createType(type);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Type> updateType(@PathVariable Integer id, @RequestBody Type type) {
        try {
            return ResponseEntity.ok(typeService.updateType(id, type));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Integer id) {
        typeService.deleteType(id);
        return ResponseEntity.noContent().build();
    }
}