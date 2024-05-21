package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.infra.exception.BadResponseException;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
public class EarningsRateForPremiumAcc implements IEarningsRateStrategy {


    @Override
    public BigDecimal getSelicDataValue(AccountType type) throws BadResponseException {
        return new BigDecimal(0.5);
    }
}
