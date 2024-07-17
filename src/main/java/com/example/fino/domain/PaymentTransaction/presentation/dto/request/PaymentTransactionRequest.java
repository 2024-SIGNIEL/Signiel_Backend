package com.example.fino.domain.PaymentTransaction.presentation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentTransactionRequest {

    private double amount;

    private String paymentTime;

    private String accountHolder;

    private String recipient;
}