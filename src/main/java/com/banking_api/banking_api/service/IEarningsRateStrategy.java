package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.infra.exception.BadResponseException;

import java.math.BigDecimal;

public interface IEarningsRateStrategy {

    public BigDecimal getSelicDataValue(AccountType type) throws BadResponseException;
}
