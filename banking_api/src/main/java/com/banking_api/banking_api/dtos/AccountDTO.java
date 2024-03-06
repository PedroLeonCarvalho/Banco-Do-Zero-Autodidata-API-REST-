package com.banking_api.banking_api.dtos;

import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.domain.user.User;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record AccountDTO(

            String accountNumber,
            BigDecimal balance,
            AccountType type,
            Date creationDate,
            LocalDateTime lastDepositDate,
            boolean active,
            Long user
    ) {}
