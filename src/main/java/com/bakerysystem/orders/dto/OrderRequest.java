package com.bakerysystem.orders.dto;

import com.bakerysystem.orders.model.OrderItem;
import com.bakerysystem.orders.model.PaymentsMetod;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItem> items;
    private PaymentsMetod paymentMethod;
}
