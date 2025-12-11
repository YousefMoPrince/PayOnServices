package com.megabyte.payonservices.DTOs;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddWalletRequest {
    private Long userId;
    private Long adminId;
    private BigDecimal balance;

}
