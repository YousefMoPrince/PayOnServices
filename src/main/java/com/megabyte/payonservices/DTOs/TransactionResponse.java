package com.megabyte.payonservices.DTOs;

import com.megabyte.payonservices.model.Status;
import com.megabyte.payonservices.model.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransactionResponse {
    private Long transaction_id;
    private Long fromUser;
    private Long toUser;
    private Long adminId;
    private BigDecimal amount;
    private TransactionType transaction_type;
    private Status status;
    private String description;

}
