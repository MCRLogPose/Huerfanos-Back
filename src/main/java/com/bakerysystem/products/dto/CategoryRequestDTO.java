package com.bakerysystem.products.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String name;
    private String description;
}
