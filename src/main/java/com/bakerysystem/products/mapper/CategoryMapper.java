package com.bakerysystem.products.mapper;

import com.bakerysystem.products.dto.CategoryRequestDTO;
import com.bakerysystem.products.dto.CategoryResponseDTO;
import com.bakerysystem.products.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDTO dto) {
        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public CategoryResponseDTO toDTO(Category entity) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }
}
