package com.banking_api.banking_api.dtos;

import com.banking_api.banking_api.domain.account.AccountType;
import org.hibernate.annotations.Fetch;

import java.util.Date;

public record AccountDTO(

            String accountNumber,
            double balance,
            AccountType type,
            Date creationDate,
            Date lastDepositDate,
            boolean active,
            Long userId
    ) {}
