package com.bakerysystem.products.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponseDTO {
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String category;
    private Double discountPercent;
    private List<ProductImageResponseDTO> images;

}