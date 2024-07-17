package com.example.fino.domain.PaymentTransaction.presentation;

import com.example.fino.domain.PaymentTransaction.presentation.dto.request.PaymentTransactionRequest;
import com.example.fino.domain.PaymentTransaction.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/payment-transactions")
@RequiredArgsConstructor
@RestController
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createFeed(@RequestBody @Valid PaymentTransactionRequest request) {
        paymentTransactionService.createFeed(request);
    }
}