package com.megabyte.payonservices.Repository;

import com.megabyte.payonservices.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(Long transactionId);
}
