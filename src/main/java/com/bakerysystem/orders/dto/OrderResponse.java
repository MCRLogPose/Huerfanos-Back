package com.bakerysystem.orders.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderCode;
    private BigDecimal total;
    private String orderStatus;
    private String paymentStatus;
    private LocalDateTime createdAt;
    private UserSummary user; // mini DTO
    private List<OrderItemResponse> items;
}
