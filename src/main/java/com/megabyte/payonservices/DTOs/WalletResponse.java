package com.megabyte.payonservices.DTOs;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class WalletResponse {
    private Long walletId;
    private Long userId;
    private BigDecimal balance;
}
