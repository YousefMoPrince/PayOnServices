package com.megabyte.payonservices.controllers;

import com.megabyte.payonservices.DTOs.AddWalletRequest;
import com.megabyte.payonservices.DTOs.ApiResponse;
import com.megabyte.payonservices.DTOs.WalletResponse;
import com.megabyte.payonservices.Repository.AdminRepo;
import com.megabyte.payonservices.Repository.UserRepo;
import com.megabyte.payonservices.Repository.WalletRepo;
import com.megabyte.payonservices.model.Admin;
import com.megabyte.payonservices.model.User;
import com.megabyte.payonservices.model.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {
    private final WalletRepo walletRepo;
    private final UserRepo userRepo;
    private final AdminRepo adminRepo;

    @PostMapping("/createwall")
    public ApiResponse<WalletResponse> createWallet(@RequestBody AddWalletRequest req) {
        User user = userRepo.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        Admin admin = adminRepo.findById(req.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin Not Found"));

        Wallet wallet = Wallet.builder()
                .user(user)
                .admin(admin)
                .balance(req.getBalance() != null ? req.getBalance() : BigDecimal.ZERO)
                .build();

        Wallet savedWallet = walletRepo.save(wallet);

        WalletResponse response = WalletResponse.builder()
                .walletId(savedWallet.getWalletId())
                .userId(user.getUserId())
                .balance(savedWallet.getBalance())
                .build();

        return new ApiResponse<>("Wallet created", response);
    }


    @GetMapping("/wallet")
    public ApiResponse<WalletResponse> getWallet(@RequestParam Long userId) {
        // 1. البحث عن المحفظة
        return walletRepo.findByUser_UserId(userId)
                .map(wall -> {

                    WalletResponse response = WalletResponse.builder()
                            .walletId(wall.getWalletId())
                            .userId(wall.getUser().getUserId())
                            .balance(wall.getBalance())
                            .build();

                    return new ApiResponse<>("success", response);
                })

                .orElseGet(() -> new ApiResponse<>("Wallet Not Found", null));


    }
}
