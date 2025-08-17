package com.paymentgateway.services;

import com.paymentgateway.dto.PaymentDTO;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthorizeNetService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizeNetService.class);

    @Value("${authorize.net.api.login.id}")
    private String apiLoginId;

    @Value("${authorize.net.transaction.key}")
    private String transactionKey;

    @Value("${authorize.net.environment:SANDBOX}")
    private String environment;

    public CreateTransactionResponse processPayment(PaymentDTO.PaymentRequest request, String transactionId) {
        try {
            // Set environment
            Environment env = "PRODUCTION".equalsIgnoreCase(environment)
                    ? Environment.PRODUCTION
                    : Environment.SANDBOX;

            ApiOperationBase.setEnvironment(env);

            // Create merchant authentication
            MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
            merchantAuthenticationType.setName(apiLoginId);
            merchantAuthenticationType.setTransactionKey(transactionKey);

            // Create credit card
            CreditCardType creditCard = new CreditCardType();
            creditCard.setCardNumber(request.getCardDetails().getCardNumber());
            creditCard.setExpirationDate(request.getCardDetails().getExpiryMonth() +
                    request.getCardDetails().getExpiryYear());
            creditCard.setCardCode(request.getCardDetails().getCvv());

            // Create payment type
            PaymentType paymentType = new PaymentType();
            paymentType.setCreditCard(creditCard);

            // Create order information
            OrderType order = new OrderType();
            order.setInvoiceNumber(transactionId);
            order.setDescription(request.getDescription());

            // Create customer information
            CustomerDataType customer = new CustomerDataType();
            customer.setType(CustomerTypeEnum.INDIVIDUAL);
            customer.setEmail(request.getCustomerEmail());

            // Create billing address (optional, but recommended)
            CustomerAddressType billingAddress = new CustomerAddressType();
            billingAddress.setFirstName(extractFirstName(request.getCustomerName()));
            billingAddress.setLastName(extractLastName(request.getCustomerName()));

            // Create transaction request
            TransactionRequestType txnRequest = new TransactionRequestType();
            txnRequest.setTransactionType(String.valueOf(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION));
            txnRequest.setAmount(request.getAmount());
            txnRequest.setPayment(paymentType);
            txnRequest.setOrder(order);
            txnRequest.setCustomer(customer);
            txnRequest.setBillTo(billingAddress);

            // Create the API request
            CreateTransactionRequest apiRequest = new CreateTransactionRequest();
            apiRequest.setMerchantAuthentication(merchantAuthenticationType);
            apiRequest.setTransactionRequest(txnRequest);

            // Call the controller
            CreateTransactionController controller = new CreateTransactionController(apiRequest);
            controller.execute();

            CreateTransactionResponse response = controller.getApiResponse();

            if (response != null) {
                logger.info("Transaction processed. Response Code: {}",
                        response.getMessages().getResultCode());

                if (response.getTransactionResponse() != null) {
                    logger.info("Transaction ID: {}, Response Code: {}",
                            response.getTransactionResponse().getTransId(),
                            response.getTransactionResponse().getResponseCode());
                }
            }

            return response;

        } catch (Exception e) {
            logger.error("Error processing payment with Authorize.Net", e);
            throw new RuntimeException("Payment processing failed: " + e.getMessage(), e);
        }
    }

    public CreateTransactionResponse refundTransaction(String transactionId, BigDecimal amount, String lastFourDigits) {
        try {
            // Set environment
            Environment env = "PRODUCTION".equalsIgnoreCase(environment)
                    ? Environment.PRODUCTION
                    : Environment.SANDBOX;

            ApiOperationBase.setEnvironment(env);

            // Create merchant authentication
            MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
            merchantAuthenticationType.setName(apiLoginId);
            merchantAuthenticationType.setTransactionKey(transactionKey);

            // Create credit card for refund (only need last 4 digits)
            CreditCardType creditCard = new CreditCardType();
            creditCard.setCardNumber("XXXX" + lastFourDigits);
            creditCard.setExpirationDate("XXXX");

            // Create payment type
            PaymentType paymentType = new PaymentType();
            paymentType.setCreditCard(creditCard);

            // Create transaction request for refund
            TransactionRequestType txnRequest = new TransactionRequestType();
            txnRequest.setTransactionType(String.valueOf(TransactionTypeEnum.REFUND_TRANSACTION));
            txnRequest.setAmount(amount);
            txnRequest.setPayment(paymentType);
            txnRequest.setRefTransId(transactionId);

            // Create the API request
            CreateTransactionRequest apiRequest = new CreateTransactionRequest();
            apiRequest.setMerchantAuthentication(merchantAuthenticationType);
            apiRequest.setTransactionRequest(txnRequest);

            // Call the controller
            CreateTransactionController controller = new CreateTransactionController(apiRequest);
            controller.execute();

            CreateTransactionResponse response = controller.getApiResponse();

            if (response != null) {
                logger.info("Refund processed. Response Code: {}",
                        response.getMessages().getResultCode());
            }

            return response;

        } catch (Exception e) {
            logger.error("Error processing refund with Authorize.Net", e);
            throw new RuntimeException("Refund processing failed: " + e.getMessage(), e);
        }
    }

    public CreateTransactionResponse voidTransaction(String transactionId) {
        try {
            // Set environment
            Environment env = "PRODUCTION".equalsIgnoreCase(environment)
                    ? Environment.PRODUCTION
                    : Environment.SANDBOX;

            ApiOperationBase.setEnvironment(env);

            // Create merchant authentication
            MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
            merchantAuthenticationType.setName(apiLoginId);
            merchantAuthenticationType.setTransactionKey(transactionKey);

            // Create transaction request for void
            TransactionRequestType txnRequest = new TransactionRequestType();
            txnRequest.setTransactionType(String.valueOf(TransactionTypeEnum.VOID_TRANSACTION));
            txnRequest.setRefTransId(transactionId);

            // Create the API request
            CreateTransactionRequest apiRequest = new CreateTransactionRequest();
            apiRequest.setMerchantAuthentication(merchantAuthenticationType);
            apiRequest.setTransactionRequest(txnRequest);

            // Call the controller
            CreateTransactionController controller = new CreateTransactionController(apiRequest);
            controller.execute();

            CreateTransactionResponse response = controller.getApiResponse();

            if (response != null) {
                logger.info("Void processed. Response Code: {}",
                        response.getMessages().getResultCode());
            }

            return response;

        } catch (Exception e) {
            logger.error("Error processing void with Authorize.Net", e);
            throw new RuntimeException("Void processing failed: " + e.getMessage(), e);
        }
    }

    private String extractFirstName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Customer";
        }
        String[] parts = fullName.trim().split("\\s+");
        return parts[0];
    }

    private String extractLastName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return "";
        }
        String[] parts = fullName.trim().split("\\s+");
        if (parts.length > 1) {
            return String.join(" ", java.util.Arrays.copyOfRange(parts, 1, parts.length));
        }
        return "";
    }

    public boolean isTransactionSuccessful(CreateTransactionResponse response) {
        if (response == null || response.getMessages() == null) {
            return false;
        }

        MessageTypeEnum resultCode = response.getMessages().getResultCode();
        return MessageTypeEnum.OK.equals(resultCode);
    }

    public String getTransactionId(CreateTransactionResponse response) {
        if (response != null && response.getTransactionResponse() != null) {
            return response.getTransactionResponse().getTransId();
        }
        return null;
    }

    public String getResponseCode(CreateTransactionResponse response) {
        if (response != null && response.getTransactionResponse() != null) {
            return response.getTransactionResponse().getResponseCode();
        }
        return null;
    }

    public String getResponseMessage(CreateTransactionResponse response) {
        if (response != null && response.getMessages() != null &&
                response.getMessages().getMessage() != null &&
                !response.getMessages().getMessage().isEmpty()) {
            return response.getMessages().getMessage().get(0).getText();
        }
        return "Unknown response";
    }
}