package com.example.fino.domain.PaymentTransaction.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentTransactionResponse {

        private Long id;
        private String accountHolder;
        private double amount;
        private String transactionDate;
        private String usageLocation;
}
