package com.bakerysystem.products.service;

import com.bakerysystem.products.dto.CategoryRequestDTO;
import com.bakerysystem.products.dto.CategoryResponseDTO;
import com.bakerysystem.products.mapper.CategoryMapper;
import com.bakerysystem.products.model.Category;
import com.bakerysystem.products.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryResponseDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDTO)
                .toList();
    }

    public CategoryResponseDTO getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        if (categoryRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        Category category = categoryMapper.toEntity(dto);
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        categoryRepository.deleteById(id);
    }
}
