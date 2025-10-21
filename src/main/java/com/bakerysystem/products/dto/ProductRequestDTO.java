package com.bakerysystem.products.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProductRequestDTO {
    private String sku;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private Double discountPercent;
    private Long categoryId;
    private List<String> images;
}