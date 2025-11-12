package com.bakerysystem.orders.repository;

import com.bakerysystem.orders.model.Order;
import com.bakerysystem.orders.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByOrderStatus(OrderStatus status);
}