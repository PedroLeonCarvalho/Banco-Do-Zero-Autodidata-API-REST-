package com.banking_api.banking_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class WithdrawDTO {
    private Long id;
    private Long accountId;
    private BigDecimal value;
    private LocalDateTime timestamp;
    private Long account;
    private BigDecimal newBalance;
}
