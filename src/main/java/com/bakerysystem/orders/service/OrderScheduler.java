package com.bakerysystem.orders.service;

import com.bakerysystem.orders.model.Order;
import com.bakerysystem.orders.model.OrderStatus;
import com.bakerysystem.orders.model.Payment;
import com.bakerysystem.orders.model.PaymentStatus;
import com.bakerysystem.orders.repository.OrderRepository;
import com.bakerysystem.orders.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    // Se ejecuta cada 5 minutos
    @Scheduled(fixedRate = 300000)
    @Transactional
    public void processAutoShipping() {
        LocalDateTime limit = LocalDateTime.now().minusMinutes(20);

        // 🔍 Buscar todos los pagos completados hace más de 20 minutos
        List<Payment> payments = paymentRepository.findAll().stream()
                .filter(p -> p.getStatus() == PaymentStatus.PAID)
                .filter(p -> p.getPaidAt() != null && p.getPaidAt().isBefore(limit))
                .toList();

        for (Payment payment : payments) {
            Order order = payment.getOrder();

            // Solo procesamos órdenes que sigan en estado PROCESSING
            if (order.getOrderStatus() == OrderStatus.PROCESSING) {
                orderService.autoShipOrder(order.getId());
                System.out.println("✅ Orden " + order.getOrderCode() + " marcada automáticamente como SHIPPED.");
            }
        }
    }
}
