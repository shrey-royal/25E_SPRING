package com.paymentgateway.services;

import com.paymentgateway.dto.PaymentDTO;
import com.paymentgateway.entities.Payment;
import com.paymentgateway.enums.PaymentStatus;
import com.paymentgateway.repositories.PaymentRepository;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AuthorizeNetService authorizeNetService;

    public PaymentDTO.PaymentResponse processPayment(PaymentDTO.PaymentRequest request) {
        logger.info("Processing payment for customer: {}, amount: {}",
                request.getCustomerEmail(), request.getAmount());

        // Generate unique transaction ID
        String transactionId = generateTransactionId();

        // Create payment entity
        Payment payment = new Payment(
                transactionId,
                request.getAmount(),
                request.getCurrency(),
                request.getCustomerEmail(),
                request.getCustomerName(),
                request.getDescription(),
                request.getPaymentMethod()
        );

        try {
            // Save initial payment record
            payment.setStatus(PaymentStatus.PROCESSING);
            payment = paymentRepository.save(payment);

            // Process payment with Authorize.Net
            CreateTransactionResponse response = authorizeNetService.processPayment(request, transactionId);

            // Update payment based on response
            updatePaymentFromResponse(payment, response);

            // Save updated payment
            payment = paymentRepository.save(payment);

            logger.info("Payment processed successfully. Transaction ID: {}, Status: {}",
                    transactionId, payment.getStatus());

            return convertToPaymentResponse(payment);

        } catch (Exception e) {
            logger.error("Error processing payment for transaction: {}", transactionId, e);

            // Update payment status to failed
            payment.setStatus(PaymentStatus.FAILED);
            payment.setResponseMessage("Payment processing failed: " + e.getMessage());
            paymentRepository.save(payment);

            throw new RuntimeException("Payment processing failed", e);
        }
    }

    public PaymentDTO.PaymentResponse refundPayment(String transactionId, BigDecimal refundAmount) {
        logger.info("Processing refund for transaction: {}, amount: {}", transactionId, refundAmount);

        Optional<Payment> paymentOpt = paymentRepository.findByTransactionId(transactionId);
        if (paymentOpt.isEmpty()) {
            throw new IllegalArgumentException("Payment not found with transaction ID: " + transactionId);
        }

        Payment payment = paymentOpt.get();

        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot refund payment with status: " + payment.getStatus());
        }

        if (refundAmount.compareTo(payment.getAmount()) > 0) {
            throw new IllegalArgumentException("Refund amount cannot exceed original payment amount");
        }

        try {
            // Get last 4 digits of card (you might need to store this during original payment)
            String lastFourDigits = "0000"; // This should be retrieved from your secure storage

            // Process refund with Authorize.Net
            CreateTransactionResponse response = authorizeNetService.refundTransaction(
                    payment.getAuthorizeTransactionId(), refundAmount, lastFourDigits);

            if (authorizeNetService.isTransactionSuccessful(response)) {
                payment.setStatus(PaymentStatus.REFUNDED);
                payment.setResponseMessage("Payment refunded successfully");
            } else {
                payment.setResponseMessage("Refund failed: " + authorizeNetService.getResponseMessage(response));
            }

            payment = paymentRepository.save(payment);

            logger.info("Refund processed for transaction: {}, status: {}", transactionId, payment.getStatus());

            return convertToPaymentResponse(payment);

        } catch (Exception e) {
            logger.error("Error processing refund for transaction: {}", transactionId, e);
            throw new RuntimeException("Refund processing failed", e);
        }
    }

    public PaymentDTO.PaymentResponse voidPayment(String transactionId) {
        logger.info("Processing void for transaction: {}", transactionId);

        Optional<Payment> paymentOpt = paymentRepository.findByTransactionId(transactionId);
        if (paymentOpt.isEmpty()) {
            throw new IllegalArgumentException("Payment not found with transaction ID: " + transactionId);
        }

        Payment payment = paymentOpt.get();

        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot void payment with status: " + payment.getStatus());
        }

        try {
            // Process void with Authorize.Net
            CreateTransactionResponse response = authorizeNetService.voidTransaction(
                    payment.getAuthorizeTransactionId());

            if (authorizeNetService.isTransactionSuccessful(response)) {
                payment.setStatus(PaymentStatus.CANCELLED);
                payment.setResponseMessage("Payment voided successfully");
            } else {
                payment.setResponseMessage("Void failed: " + authorizeNetService.getResponseMessage(response));
            }

            payment = paymentRepository.save(payment);

            logger.info("Void processed for transaction: {}, status: {}", transactionId, payment.getStatus());

            return convertToPaymentResponse(payment);

        } catch (Exception e) {
            logger.error("Error processing void for transaction: {}", transactionId, e);
            throw new RuntimeException("Void processing failed", e);
        }
    }

    @Transactional(readOnly = true)
    public PaymentDTO.PaymentResponse getPaymentByTransactionId(String transactionId) {
        Optional<Payment> payment = paymentRepository.findByTransactionId(transactionId);
        return payment.map(this::convertToPaymentResponse)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with transaction ID: " + transactionId));
    }

    @Transactional(readOnly = true)
    public List<PaymentDTO.PaymentResponse> getPaymentsByCustomerEmail(String customerEmail) {
        List<Payment> payments = paymentRepository.findByCustomerEmailOrderByCreatedAtDesc(customerEmail);
        return payments.stream()
                .map(this::convertToPaymentResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<PaymentDTO.PaymentResponse> getAllPayments(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAll(pageable);
        return payments.map(this::convertToPaymentResponse);
    }

    @Transactional(readOnly = true)
    public Page<PaymentDTO.PaymentResponse> getPaymentsByStatus(PaymentStatus status, Pageable pageable) {
        Page<Payment> payments = paymentRepository.findByStatus(status, pageable);
        return payments.map(this::convertToPaymentResponse);
    }

    @Transactional(readOnly = true)
    public Page<PaymentDTO.PaymentResponse> searchPayments(String customerEmail,
                                                           PaymentStatus status,
                                                           BigDecimal minAmount,
                                                           BigDecimal maxAmount,
                                                           LocalDateTime startDate,
                                                           LocalDateTime endDate,
                                                           Pageable pageable) {
        Page<Payment> payments = paymentRepository.findPaymentsByCriteria(
                customerEmail, status, minAmount, maxAmount, startDate, endDate, pageable);
        return payments.map(this::convertToPaymentResponse);
    }

    private void updatePaymentFromResponse(Payment payment, CreateTransactionResponse response) {
        if (authorizeNetService.isTransactionSuccessful(response)) {
            payment.setStatus(PaymentStatus.COMPLETED);
            payment.setAuthorizeTransactionId(authorizeNetService.getTransactionId(response));
            payment.setResponseCode(authorizeNetService.getResponseCode(response));
            payment.setResponseMessage("Payment completed successfully");
        } else {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setResponseCode(authorizeNetService.getResponseCode(response));
            payment.setResponseMessage(authorizeNetService.getResponseMessage(response));
        }
    }

    private PaymentDTO.PaymentResponse convertToPaymentResponse(Payment payment) {
        PaymentDTO.PaymentResponse response = new PaymentDTO.PaymentResponse();
        response.setId(payment.getId());
        response.setTransactionId(payment.getTransactionId());
        response.setAmount(payment.getAmount());
        response.setCurrency(payment.getCurrency());
        response.setCustomerEmail(payment.getCustomerEmail());
        response.setCustomerName(payment.getCustomerName());
        response.setDescription(payment.getDescription());
        response.setStatus(payment.getStatus());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setAuthorizeTransactionId(payment.getAuthorizeTransactionId());
        response.setResponseCode(payment.getResponseCode());
        response.setResponseMessage(payment.getResponseMessage());
        response.setCreatedAt(payment.getCreatedAt());
        response.setUpdatedAt(payment.getUpdatedAt());
        return response;
    }

    private String generateTransactionId() {
        return "TXN-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}