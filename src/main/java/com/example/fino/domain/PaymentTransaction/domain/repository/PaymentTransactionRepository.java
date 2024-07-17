package com.example.fino.domain.PaymentTransaction.domain.repository;

import com.example.fino.domain.PaymentTransaction.domain.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
}