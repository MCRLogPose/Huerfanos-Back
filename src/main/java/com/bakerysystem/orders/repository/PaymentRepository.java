package com.bakerysystem.orders.repository;

import com.bakerysystem.orders.model.Order;
import com.bakerysystem.orders.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrder(Order order);
}
