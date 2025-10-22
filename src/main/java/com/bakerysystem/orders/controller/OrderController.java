package com.bakerysystem.orders.controller;

import com.bakerysystem.orders.dto.OrderResponse;
import com.bakerysystem.orders.mapper.OrderMapper;
import com.bakerysystem.orders.model.Order;
import com.bakerysystem.orders.model.OrderItem;
import com.bakerysystem.orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 🟢 Crear una nueva orden
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestParam Long userId, @RequestBody List<OrderItem> items) {
        Order order = orderService.createOrder(userId, items);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    // 🟢 Obtener todas las órdenes
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrderResponses());
    }

    // 🟢 Obtener órdenes por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserResponse(userId));
    }

    // 🟢 Obtener una orden por ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    // 🟢 Confirmar pago
    @PostMapping("/{id}/confirm-payment")
    public ResponseEntity<OrderResponse> confirmPayment(@PathVariable Long id) {
        Order order = orderService.confirmPayment(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    // 🟢 Cancelar orden
    @PostMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id) {
        Order order = orderService.cancelOrder(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    // 🟢 Cambiar a estado SHIPPER
    @PostMapping("/{id}/shipper")
    public ResponseEntity<OrderResponse> markAsShipper(@PathVariable Long id) {
        Order order = orderService.markAsShipper(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    // 🟢 Cambiar a estado DELIVERED
    @PostMapping("/{id}/delivered")
    public ResponseEntity<OrderResponse> markAsDelivered(@PathVariable Long id) {
        Order order = orderService.markAsDelivered(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    // 🟢 Cambiar a estado COMPLETED
    @PostMapping("/{id}/completed")
    public ResponseEntity<OrderResponse> markAsCompleted(@PathVariable Long id) {
        Order order = orderService.markAsCompleted(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

}
