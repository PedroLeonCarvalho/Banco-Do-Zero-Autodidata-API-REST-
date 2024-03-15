package com.banking_api.banking_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WithdrawDTO(
        Long id,
        Long accountId,
        BigDecimal value,
        LocalDateTime timestamp,
        Long account,
        BigDecimal newBalance

) {
}
