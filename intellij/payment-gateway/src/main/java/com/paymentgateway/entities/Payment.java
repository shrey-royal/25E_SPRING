package com.paymentgateway.entities;

import com.paymentgateway.enums.PaymentMethod;
import com.paymentgateway.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionId;
    private BigDecimal amount;
    private String currency = "USD";
    private String customerEmail;
    private String customerName;
    private String description;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String authorizeTransactionId;
    private String responseCode;
    private String responseMessage;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}

