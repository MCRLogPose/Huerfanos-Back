package com.bakerysystem.orders.dto;

import com.bakerysystem.products.dto.ProductImageResponseDTO;
import com.bakerysystem.products.model.ProductImage;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummary {
    private Long id;
    private String name;
    private Double price;
    private String category;
    private Double discountPercent;
    private List<ProductImageResponseDTO> images;
}
