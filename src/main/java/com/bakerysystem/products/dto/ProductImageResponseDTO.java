package com.bakerysystem.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponseDTO {
    private String url;
    private String altText;
    private Integer position;
    private String provider;
}