package com.projet.furniture_platform.service;


import com.projet.furniture_platform.entity.Type;
import com.projet.furniture_platform.repository.TypeRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class TypeService {


    private final TypeRepository typeRepository;


    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }


    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }


    public Optional<Type> getTypeById(Integer id) {
        return typeRepository.findById(id);
    }


    public Type createType(Type type) {
        return typeRepository.save(type);
    }


    public Type updateType(Integer id, Type updatedType) {
        return typeRepository.findById(id).map(type -> {
            type.setName(updatedType.getName());
            return typeRepository.save(type);
        }).orElseThrow(() -> new RuntimeException("Type not found"));
    }


    public void deleteType(Integer id) {
        typeRepository.deleteById(id);
    }
}