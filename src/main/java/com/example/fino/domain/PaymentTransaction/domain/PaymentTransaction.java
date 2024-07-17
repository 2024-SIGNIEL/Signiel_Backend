package com.example.fino.domain.PaymentTransaction.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String paymentTime;

    @Column(nullable = false)
    private String accountHolder;

    @Column(nullable = false)
    private String recipient;

    @Builder
    public PaymentTransaction(Long id, double amount, String paymentTime, String accountHolder, String recipient) {
        this.id = id;
        this.amount = amount;
        this.paymentTime = paymentTime;
        this.accountHolder = accountHolder;
        this.recipient = recipient;
    }
}
