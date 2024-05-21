package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.AccountType;
import org.springframework.stereotype.Component;

@Component
public class AccountTypeFactory {

    public IEarningsRateStrategy strategyMake (AccountType type) {

        switch (type) {
            case POUPANCA:
                return new EarningsRateForNormalAccount();

            case PREMIUM:
                return new EarningsRateForPremiumAcc();

            default:

                throw new IllegalArgumentException("Tipo de conta desconhecido: " + type);
        }
    }

}
