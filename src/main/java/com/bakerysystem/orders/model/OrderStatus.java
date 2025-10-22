package com.bakerysystem.orders.model;

public enum OrderStatus {
    PENDING,        // En carrito, sin pagar
    PROCESSING,     // Se inició el pago
    SHIPPED,        // Pago exitoso → preparando envío
    DELIVERED,      // Enviado al cliente
    COMPLETED,      // Confirmado como entregado
    CANCELLED       // Cancelado después del pago (con reembolso)
}
