package com.paymentgateway.repositories;

import com.paymentgateway.entities.Payment;
import com.paymentgateway.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Find payment by transaction ID
    Optional<Payment> findByTransactionId(String transactionId);

    // Find payment by Authorize.Net transaction ID
    Optional<Payment> findByAuthorizeTransactionId(String authorizeTransactionId);

    // Find payments by customer email
    List<Payment> findByCustomerEmailOrderByCreatedAtDesc(String customerEmail);

    // Find payments by status
    List<Payment> findByStatusOrderByCreatedAtDesc(PaymentStatus status);

    // Find payments by status with pagination
    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);

    // Find payments by customer email with pagination
    Page<Payment> findByCustomerEmail(String customerEmail, Pageable pageable);

    // Find payments by amount range
    List<Payment> findByAmountBetweenOrderByCreatedAtDesc(BigDecimal minAmount, BigDecimal maxAmount);

    // Find payments by date range
    @Query("SELECT p FROM Payment p WHERE p.createdAt BETWEEN :startDate AND :endDate ORDER BY p.createdAt DESC")
    List<Payment> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate);

    // Find payments by date range with pagination
    @Query("SELECT p FROM Payment p WHERE p.createdAt BETWEEN :startDate AND :endDate ORDER BY p.createdAt DESC")
    Page<Payment> findByDateRange(@Param("startDate") LocalDateTime startDate,
                                  @Param("endDate") LocalDateTime endDate,
                                  Pageable pageable);

    // Count payments by status
    long countByStatus(PaymentStatus status);

    // Sum total amount by status
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") PaymentStatus status);

    // Find recent payments (last 24 hours)
    @Query("SELECT p FROM Payment p WHERE p.createdAt >= :since ORDER BY p.createdAt DESC")
    List<Payment> findRecentPayments(@Param("since") LocalDateTime since);

    // Find payments by multiple criteria
    @Query("SELECT p FROM Payment p WHERE " +
            "(:customerEmail IS NULL OR p.customerEmail = :customerEmail) AND " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(:minAmount IS NULL OR p.amount >= :minAmount) AND " +
            "(:maxAmount IS NULL OR p.amount <= :maxAmount) AND " +
            "(:startDate IS NULL OR p.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR p.createdAt <= :endDate) " +
            "ORDER BY p.createdAt DESC")
    Page<Payment> findPaymentsByCriteria(@Param("customerEmail") String customerEmail,
                                         @Param("status") PaymentStatus status,
                                         @Param("minAmount") BigDecimal minAmount,
                                         @Param("maxAmount") BigDecimal maxAmount,
                                         @Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate,
                                         Pageable pageable);

    // Check if transaction ID exists
    boolean existsByTransactionId(String transactionId);

    // Find failed payments for retry
    @Query("SELECT p FROM Payment p WHERE p.status = 'FAILED' AND p.createdAt >= :since ORDER BY p.createdAt DESC")
    List<Payment> findFailedPaymentsForRetry(@Param("since") LocalDateTime since);

    // Dashboard statistics
    @Query("SELECT new map(" +
            "COUNT(p) as totalTransactions, " +
            "COALESCE(SUM(CASE WHEN p.status = 'COMPLETED' THEN p.amount ELSE 0 END), 0) as totalRevenue, " +
            "COUNT(CASE WHEN p.status = 'COMPLETED' THEN 1 END) as successfulTransactions, " +
            "COUNT(CASE WHEN p.status = 'FAILED' THEN 1 END) as failedTransactions" +
            ") FROM Payment p WHERE p.createdAt >= :since")
    Object getDashboardStats(@Param("since") LocalDateTime since);
}
