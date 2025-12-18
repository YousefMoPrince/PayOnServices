package com.megabyte.payonservices.controllers;

import com.megabyte.payonservices.DTOs.ApiResponse;
import com.megabyte.payonservices.DTOs.TransactionRequest;
import com.megabyte.payonservices.DTOs.TransactionResponse;
import com.megabyte.payonservices.Repository.AdminRepo;
import com.megabyte.payonservices.Repository.TransactionRepo;
import com.megabyte.payonservices.Repository.UserRepo;
import com.megabyte.payonservices.Repository.WalletRepo;
import com.megabyte.payonservices.model.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final WalletRepo walletRepo;
    private final TransactionRepo transactionRepo;
    private final AdminRepo adminRepo;
    private final UserRepo userRepo;

    @PostMapping("/deposit")
    @Transactional
    public ApiResponse<TransactionResponse> deposit(@RequestBody TransactionRequest req) {
        User user = userRepo.findById(req.getFromUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        Admin admin = adminRepo.findById(req.getAdminId()).orElseThrow(() -> new RuntimeException("Admin Not Found"));
        Wallet wallet = walletRepo.findByUser_UserId(req.getFromUserId())
                .orElseThrow(() -> new RuntimeException("Wallet Not Found"));
        wallet.setBalance(wallet.getBalance().add(req.getAmount()));
        walletRepo.save(wallet);
        Transaction tx = Transaction.builder().fromUser(user).admin(admin).amount(req.getAmount())
                .description(req.getDescription()).transaction_type(TransactionType.DEPOSIT).status(Status.COMPLETED)
                .build();
        transactionRepo.save(tx);
        TransactionResponse response = TransactionResponse.builder().transaction_id(tx.getTransactionId())
                .fromUser(user.getUserId()).adminId(admin.getAdminId()).amount(tx.getAmount())
                .description(tx.getDescription()).transaction_type(tx.getTransaction_type()).status(tx.getStatus())
                .build();
        return new ApiResponse<>("Deposit Successful", response);
    }

    @PostMapping("/withdraw")
    @Transactional
    public ApiResponse<TransactionResponse> withdraw(@RequestBody TransactionRequest req) {
        User user = userRepo.findById(req.getFromUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        Admin admin = adminRepo.findById(req.getAdminId()).orElseThrow(() -> new RuntimeException("Admin Not Found"));
        Transaction tx = Transaction.builder().fromUser(user).toUser(user).admin(admin).amount(req.getAmount())
                .description(req.getDescription()).transaction_type(TransactionType.WITHDRAW).status(Status.PENDING)
                .build();
        transactionRepo.save(tx);
        TransactionResponse response = TransactionResponse.builder().transaction_id(tx.getTransactionId())
                .fromUser(user.getUserId()).toUser(user.getUserId()).adminId(admin.getAdminId()).amount(tx.getAmount())
                .description(tx.getDescription()).transaction_type(tx.getTransaction_type()).status(tx.getStatus())
                .build();
        return new ApiResponse<>("Withdrawal Status pending", response);
    }

    @PutMapping("/{transactionId}/withdrawstatus")
    @Transactional
    public ApiResponse<TransactionResponse> withdrawStatus(@PathVariable Long transactionId,
            @RequestBody Status newStatus) {
        Transaction tx = transactionRepo.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction Not Found"));

        if (newStatus == Status.COMPLETED) {
            Wallet fromWallet = walletRepo.findByUser_UserId(tx.getFromUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("Sender Wallet Not Found"));

            if (fromWallet.getBalance().compareTo(tx.getAmount()) < 0) {
                tx.setStatus(Status.FAILED);
                transactionRepo.save(tx);
                return new ApiResponse<>("Insufficient balance", null);
            } else {
                fromWallet.setBalance(fromWallet.getBalance().subtract(tx.getAmount()));
                walletRepo.save(fromWallet);
                tx.setStatus(Status.COMPLETED);
            }
        } else {
            tx.setStatus(newStatus);
        }

        transactionRepo.save(tx);

        TransactionResponse response = TransactionResponse.builder()
                .transaction_id(tx.getTransactionId())
                .fromUser(tx.getFromUser().getUserId())
                .toUser(tx.getToUser().getUserId())
                .adminId(tx.getAdmin().getAdminId())
                .amount(tx.getAmount())
                .description(tx.getDescription())
                .transaction_type(tx.getTransaction_type())
                .status(tx.getStatus())
                .build();

        return new ApiResponse<>("Withdrawal Status Updated", response);
    }

    @PostMapping("/transfer")
    @Transactional
    public ApiResponse<TransactionResponse> transfer(@RequestBody TransactionRequest req) {
        User fromuser = userRepo.findById(req.getFromUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        User touser = userRepo.findById(req.getToUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
        Admin admin = adminRepo.findById(req.getAdminId()).orElseThrow(() -> new RuntimeException("Admin Not Found"));
        Transaction tx = Transaction.builder().fromUser(fromuser).toUser(touser).admin(admin).amount(req.getAmount())
                .description(req.getDescription()).transaction_type(TransactionType.TRANSFER).status(Status.PENDING)
                .build();
        transactionRepo.save(tx);
        TransactionResponse response = TransactionResponse.builder().transaction_id(tx.getTransactionId())
                .fromUser(fromuser.getUserId()).toUser(touser.getUserId()).adminId(admin.getAdminId())
                .amount(tx.getAmount()).description(tx.getDescription()).transaction_type(tx.getTransaction_type())
                .status(tx.getStatus()).build();
        return new ApiResponse<>("Transfer Status Pending", response);
    }

    @PutMapping("/{transactionId}/transferstatus")
    @Transactional
    public ApiResponse<TransactionResponse> TransferStatus(@PathVariable Long transactionId,
            @RequestBody Status newStatus) {
        Transaction tx = transactionRepo.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction Not Found"));
        if (newStatus == Status.COMPLETED) {
            Wallet fromWallet = walletRepo.findByUser_UserId(tx.getFromUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("Sender Wallet Not Found"));
            Wallet toWallet = walletRepo.findByUser_UserId(tx.getToUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("Sender Wallet Not Found"));
            if (fromWallet.getBalance().compareTo(tx.getAmount()) < 0) {
                tx.setStatus(Status.FAILED);
                return new ApiResponse<>("Insufficient balance", null);
            } else {
                fromWallet.setBalance(fromWallet.getBalance().subtract(tx.getAmount()));
                walletRepo.save(fromWallet);
                toWallet.setBalance(toWallet.getBalance().add(tx.getAmount()));
                walletRepo.save(toWallet);
                tx.setStatus(Status.COMPLETED);
            }
        } else {
            tx.setStatus(newStatus);
        }
        transactionRepo.save(tx);
        TransactionResponse response = TransactionResponse.builder()
                .transaction_id(tx.getTransactionId())
                .fromUser(tx.getFromUser().getUserId())
                .toUser(tx.getToUser().getUserId())
                .adminId(tx.getAdmin().getAdminId())
                .amount(tx.getAmount())
                .description(tx.getDescription())
                .transaction_type(tx.getTransaction_type())
                .status(tx.getStatus())
                .build();

        return new ApiResponse<>("Transfer Status Updated", response);

    }

}
