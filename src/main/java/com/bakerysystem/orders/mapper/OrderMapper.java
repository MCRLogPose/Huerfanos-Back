package com.bakerysystem.orders.mapper;

import com.bakerysystem.orders.dto.*;
import com.bakerysystem.orders.model.Order;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        if (order == null) return null;

        return OrderResponse.builder()
                .id(order.getId())
                .orderCode(order.getOrderCode())
                .total(order.getTotal())
                .orderStatus(order.getOrderStatus().name())
                .paymentStatus(order.getPaymentStatus().name())
                .createdAt(order.getCreatedAt())
                .user(order.getUser() != null ? UserSummary.builder()
                        .id(order.getUser().getId())
                        .firstName(order.getUser().getFirstName())
                        .lastName(order.getUser().getLastName())
                        .email(order.getUser().getEmail())
                        .build() : null)
                .items(order.getItems() != null ? order.getItems().stream()
                        .map(item -> OrderItemResponse.builder()
                                .id(item.getId())
                                .quantity(item.getQuantity())
                                .price(item.getPrice())
                                .product(item.getProduct() != null ? ProductSummary.builder()
                                        .id(item.getProduct().getId())
                                        .name(item.getProduct().getName())
                                        .price(item.getProduct().getPrice())
                                        .category(item.getProduct().getCategory() != null ?
                                                item.getProduct().getCategory().getName() : null)
                                        .build() : null)
                                .build())
                        .collect(Collectors.toList()) : null)
                .build();
    }
}
