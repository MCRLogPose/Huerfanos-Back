package com.bakerysystem.orders.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummary {
    private Long id;
    private String name;
    private Double price;
    private String category;
}
