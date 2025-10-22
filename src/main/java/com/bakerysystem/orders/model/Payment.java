package com.bakerysystem.orders.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la orden
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Monto total del pago
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    // Estado del pago
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    // Código de referencia del pago (de la pasarela o interno)
    private String transactionId;

    // Fecha del pago
    private LocalDateTime paidAt;

    // Fecha del reembolso, si aplica
    private LocalDateTime refundedAt;

    // Método de pago (tarjeta, Yape, etc.)
    private String paymentMethod;

    // Detalle opcional (mensaje de pasarela o error)
    private String description;
}
