package com.bakerysystem.orders.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private ProductSummary product;
}
