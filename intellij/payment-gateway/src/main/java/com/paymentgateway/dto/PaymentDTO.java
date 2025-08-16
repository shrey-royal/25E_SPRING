package com.paymentgateway.dto;

import com.paymentgateway.enums.PaymentMethod;
import com.paymentgateway.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO {
    @Data
    @Builder
    public static class PaymentRequest {
        private BigDecimal amount;
        @Builder.Default
        private String currency = "USD";
        private String customerEmail;
        private String customerName;
        private String description;
        private PaymentMethod paymentMethod;
        private CardDetails cardDetails;
    }

    @Data
    @Builder
    public static class PaymentResponse {
        private Long id;
        private String transactionId;
        private BigDecimal amount;
        private String currency;
        private String customerEmail;
        private PaymentStatus status;
        private String authorizeTransactionId;
        private String responseMessage;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    @ToString(exclude = {"cardNumber", "cvv"})
    public static class CardDetails {
        private String cardNumber;
        private String expiryMonth;
        private String expiryYear;
        private String cvv;
        private String cardHolderName;
    }

    @Data
    @Builder
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        private String error;
        @Builder.Default
        private LocalDateTime timestamp = LocalDateTime.now();

        public static <T> ApiResponse<T> success(String message, T data) {
            return ApiResponse.<T>builder()
                    .success(true)
                    .message(message)
                    .data(data)
                    .build();
        }

        public static <T> ApiResponse<T> error(String message, String error) {
            return ApiResponse.<T>builder()
                    .success(false)
                    .message(message)
                    .error(error)
                    .build();
        }
    }
}
