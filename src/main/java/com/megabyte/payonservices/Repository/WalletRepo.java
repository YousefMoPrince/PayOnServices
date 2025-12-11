package com.megabyte.payonservices.Repository;

import com.megabyte.payonservices.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepo extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUser_UserId(Long userId);
    Optional<Wallet> findByAdmin_AdminId(Long adminId);
}
