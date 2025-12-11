package com.megabyte.payonservices.DTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private Long fromUserId;
    private Long toUserId;
    private Long adminId;
    private BigDecimal amount;
    private String description;
}
