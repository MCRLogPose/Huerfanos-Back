package com.bakerysystem.orders.repository;

import com.bakerysystem.orders.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}