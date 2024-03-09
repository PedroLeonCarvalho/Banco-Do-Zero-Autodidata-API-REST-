package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.transactions.deposit.Deposit;
import com.banking_api.banking_api.dtos.DepositDTO;
import com.banking_api.banking_api.repository.DepositRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DepositService {

    private final DepositRepository repository;
    private final AccountService accountService;

    public DepositService(DepositRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Transactional
    public Deposit deposit(DepositDTO dto) throws EntityNotFoundException {
        var account = accountService.findByAccountId(dto.accountId());
        var value = dto.value();

        var newBalance = account.getBalance().add(value);
        account.setBalance(newBalance);
        accountService.save(account);

        Deposit newDeposit = new Deposit();
        newDeposit.setValue(value);
        newDeposit.setTimestamp(LocalDateTime.now());
        newDeposit.setAccount(account);

        repository.save(newDeposit);
        return newDeposit;
    }

    public LocalDateTime getLastDepositDate() {

        var lastDepositDate = repository.getLastDepositDate();
        Deposit deposit = new Deposit();
        var depositTimestamp = deposit.getTimestamp();
        if (depositTimestamp != null) {
            return depositTimestamp;
        } else return null;
    }
}
