package com.megabyte.payonservices.Repository;

import com.megabyte.payonservices.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<BankAccount,Long> {
    boolean existsByAccountNumber(String accountNumber);

    Optional<BankAccount> findByUserId_UserId(Long userId);
}
