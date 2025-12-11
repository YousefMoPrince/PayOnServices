package com.megabyte.payonservices.Repository;

import com.megabyte.payonservices.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<BankAccount,Long> {
    boolean existsByAccountNumber(String accountNumber);
   BankAccount findOneByAccountNumber(String accountNumber);
}
