package com.paymentgateway.controller;

import com.paymentgateway.service.PaymentService;
import net.authorize.api.contract.v1.ANetApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public String hello() {
        return "Hello World!";
    }

    @PostMapping
    public ANetApiResponse creditCardPayment(@RequestBody Map<String, String> body) {
        String creditCardNumber = body.get("creditCardNumber");
        String expDate = body.get("expDate");
        String email = body.get("email");

        return paymentService.chargeCreditCard(creditCardNumber, 1999.0, email, expDate);
    }
}
