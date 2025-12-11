package com.megabyte.payonservices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long account_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
    @Column(name = "bank_name", nullable = false,length = 100)
    private String bankName;
    @Column(name = "account_number", nullable = false,length = 16)
    private String accountNumber;
    @Column(name = "account_holder", nullable = false,length = 255)
    private String accountHolder;
}
