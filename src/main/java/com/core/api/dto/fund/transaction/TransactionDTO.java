package com.core.api.dto.fund.transaction;

import com.core.api.Enums.TypeTransactionsEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDTO {
    private UUID transactionId;
    private BigDecimal amount;
    private TypeTransactionsEnum status;
}
