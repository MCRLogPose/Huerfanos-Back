package com.bakerysystem.orders.service;

import com.bakerysystem.auth.model.User;
import com.bakerysystem.orders.dto.OrderItemResponse;
import com.bakerysystem.orders.dto.OrderResponse;
import com.bakerysystem.orders.dto.ProductSummary;
import com.bakerysystem.orders.dto.UserSummary;
import com.bakerysystem.orders.mapper.OrderMapper;
import com.bakerysystem.orders.model.*;
import com.bakerysystem.orders.repository.*;
import com.bakerysystem.products.model.Product;
import com.bakerysystem.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;

    // ✅ Obtener todas las órdenes con DTO (sin recursividad)
    public List<OrderResponse> getAllOrderResponses() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponse) // llamamos al mapper
                .collect(Collectors.toList());
    }

    // ✅ Obtener órdenes de un usuario con DTO
    public List<OrderResponse> getOrdersByUserResponse(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }


    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.paymentRepository = paymentRepository;
    }

    // ✅ Crear una nueva orden (carrito -> pendiente)
    @Transactional
    public Order createOrder(Long userId, List<OrderItem> items, PaymentsMetod paymentMethod) {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            // Convertir precio y descuento de Double a BigDecimal
            BigDecimal price = BigDecimal.valueOf(product.getPrice());
            BigDecimal discountRate = BigDecimal.ZERO;

            if (product.getDiscountPercent() != null && product.getDiscountPercent() > 0) {
                discountRate = BigDecimal.valueOf(product.getDiscountPercent())
                        .divide(BigDecimal.valueOf(100));
            }

            // Calcular precio final con descuento
            BigDecimal finalPrice = price.multiply(BigDecimal.ONE.subtract(discountRate));

            // Calcular total del ítem
            BigDecimal itemTotal = finalPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

            item.setProduct(product);
            item.setPrice(finalPrice); // finalPrice sigue siendo BigDecimal
            total = total.add(itemTotal);
        }

        // Generar código único
        String orderCode = "ORD-" + System.currentTimeMillis();

        // Crear orden
        Order order = Order.builder()
                .orderCode(orderCode)
                .user(User.builder().id(userId).build())
                .items(items)
                .total(total)
                .paymentMethod(paymentMethod.toString())
                .orderStatus(OrderStatus.PENDING)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        items.forEach(item -> item.setOrder(order));

        return orderRepository.save(order);
    }

    // ✅ Obtener orden por ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
    }

    // ✅ Obtener todas las órdenes de un usuario
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    // ✅ Confirmar pago
    @Transactional
    public Order confirmPayment(Long orderId) {
        Order order = getOrderById(orderId);

        if (order.getOrderStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("La orden no está en estado pendiente");
        }

        // Simular pago exitoso
        Payment payment = Payment.builder()
                .order(order)
                .status(PaymentStatus.PAID)
                .amount(order.getTotal())
                .paidAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        // Actualizar stock
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // Actualizar estados
        order.setOrderStatus(OrderStatus.PROCESSING);
        order.setPaymentStatus(PaymentStatus.PAID);
        order.setPaidAt(LocalDateTime.now());
        orderRepository.save(order);

        return order;
    }

    // ✅ Cancelar orden (solo dentro de 4 horas)
    @Transactional
    public Order cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);

        if (order.getOrderStatus() != OrderStatus.PROCESSING) {
            throw new RuntimeException("Solo se pueden cancelar órdenes en proceso");
        }

        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        // Verificar límite de 4 horas
        if (payment.getPaidAt().plusHours(4).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("No se puede cancelar después de 4 horas");
        }

        // Reintegrar stock
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        // Actualizar estados
        payment.setStatus(PaymentStatus.REFUNDED);
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setPaymentStatus(PaymentStatus.REFUNDED);
        order.setClosedAt(LocalDateTime.now());
        paymentRepository.save(payment);
        orderRepository.save(order);

        return order;
    }

    // ✅ Listar todas las órdenes (admin)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order markAsShipper(Long orderId) {
        Order order = getOrderById(orderId);

        if (order.getOrderStatus() != OrderStatus.PROCESSING) {
            throw new RuntimeException("La orden debe estar en estado PROCESSING para pasar a SHIPPER.");
        }

        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado."));

        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order markAsDelivered(Long orderId) {
        Order order = getOrderById(orderId);

        if (order.getOrderStatus() != OrderStatus.SHIPPED) {
            throw new RuntimeException("La orden debe estar en estado SHIPPER para marcarla como DELIVERED.");
        }

        order.setOrderStatus(OrderStatus.DELIVERED);
        return orderRepository.save(order);
    }

    @Transactional
    public Order markAsCompleted(Long orderId) {
        Order order = getOrderById(orderId);

        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new RuntimeException("La orden debe estar en estado DELIVERED para marcarla como COMPLETED.");
        }

        order.setOrderStatus(OrderStatus.COMPLETED);
        order.setClosedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Transactional
    public void autoShipOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        if (order.getOrderStatus() == OrderStatus.PROCESSING) {
            order.setOrderStatus(OrderStatus.SHIPPED);
            orderRepository.save(order);
        }
    }

}
