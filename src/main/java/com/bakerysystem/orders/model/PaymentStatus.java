package com.bakerysystem.orders.model;

public enum PaymentStatus {
    PENDING,    // Pago iniciado
    PAID,       // Pago completado
    FAILED,     // Pago rechazado
    REFUNDED,    // Pago devuelto tras cancelación
    CANCELLED    // Cancelado antes de completarse
}
