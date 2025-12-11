package com.megabyte.payonservices.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankResponse {
    private String bankName;
    private String accountHolder;
            private String accountNumber;
}
