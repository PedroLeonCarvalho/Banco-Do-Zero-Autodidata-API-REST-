package com.banking_api.banking_api.dtos;

import com.banking_api.banking_api.domain.account.Account;

import java.math.BigDecimal;

public record DepositDTO(
        Long accountId,
        BigDecimal value
) {
}
