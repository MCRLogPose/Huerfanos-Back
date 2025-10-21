package com.bakerysystem.products.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductImageResponseDTO {
    private String url;
    private String altText;
    private Integer position;
    private String provider;
}