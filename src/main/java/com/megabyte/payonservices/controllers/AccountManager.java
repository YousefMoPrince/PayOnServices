package com.megabyte.payonservices.controllers;

import com.megabyte.payonservices.DTOs.AddBankRequest;
import com.megabyte.payonservices.DTOs.ApiResponse;
import com.megabyte.payonservices.DTOs.BankResponse;
import com.megabyte.payonservices.Repository.AccountRepo;
import com.megabyte.payonservices.Repository.AdminRepo;
import com.megabyte.payonservices.Repository.UserRepo;
import com.megabyte.payonservices.model.Admin;
import com.megabyte.payonservices.model.BankAccount;
import com.megabyte.payonservices.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accmanager")
@RequiredArgsConstructor
public class AccountManager {
private final AccountRepo accountRepo;
private final AdminRepo adminRepo;
private final UserRepo userRepo;


@PostMapping("/accountreg")
    public ApiResponse<BankResponse> registerAccount(@RequestBody AddBankRequest req) {
    if (accountRepo.existsByAccountNumber(req.getAccountNumber())) {
        return new ApiResponse<>("this account exists", null);
    }

    User user = userRepo.findById(req.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
    Admin admin = adminRepo.findById(req.getAdminId())
            .orElseThrow(() -> new RuntimeException("User not found"));


    BankAccount bankAccount = BankAccount.builder().bankName(req.getBankName()).accountHolder(req.getAccountHolder()).accountNumber(req.getAccountNumber()).userId(user).adminId(admin).build();
accountRepo.save(bankAccount);
BankResponse response = BankResponse.builder().bankName(req.getBankName()).accountHolder(req.getAccountHolder()).accountNumber(req.getAccountNumber()).build();
return new ApiResponse<>("success", response);
}

    @GetMapping("/findacc")
    public ApiResponse<BankResponse> findAccount(@RequestParam Long userId) {

        return accountRepo.findByUserId_UserId(userId)
                .map(accNum -> {
                    BankResponse response = BankResponse.builder()
                            .bankName(accNum.getBankName())
                            .accountHolder(accNum.getAccountHolder())
                            .accountNumber(accNum.getAccountNumber())
                            .build();
                    return new ApiResponse<>("success", response);
                })
                .orElseGet(() -> new ApiResponse<>("Account not found", null));
    }

}
