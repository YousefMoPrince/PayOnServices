package com.megabyte.payonservices.DTOs;

import lombok.Data;

@Data
public class AddBankRequest {
    private String bankName;
    private String accountNumber;
    private String accountHolder;
    private Long userId;
    private Long adminId;
}
