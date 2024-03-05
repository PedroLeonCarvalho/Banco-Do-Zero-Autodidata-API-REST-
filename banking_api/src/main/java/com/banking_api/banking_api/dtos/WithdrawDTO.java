package com.banking_api.banking_api.dtos;

import java.math.BigDecimal;

public record WithdrawDTO(
        Long accountId,
        BigDecimal value
) {
}
