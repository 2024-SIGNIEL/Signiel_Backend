package com.example.fino.domain.PaymentTransaction.service;

import com.example.fino.domain.PaymentTransaction.domain.PaymentTransaction;
import com.example.fino.domain.PaymentTransaction.domain.repository.PaymentTransactionRepository;
import com.example.fino.domain.PaymentTransaction.presentation.dto.request.PaymentTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentTransactionService {

    private final PaymentTransactionRepository paymentTransactionRepository;


    @Transactional
    public void createFeed(PaymentTransactionRequest request) {

        paymentTransactionRepository.save(PaymentTransaction.builder()
                .amount(request.getAmount())
                .paymentTime(request.getPaymentTime())
                .recipient(request.getRecipient())
                .accountHolder(request.getAccountHolder())
                .build());
    }
}
