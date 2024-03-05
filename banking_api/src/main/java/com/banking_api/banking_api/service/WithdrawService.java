package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.transactions.deposit.Deposit;
import com.banking_api.banking_api.domain.transactions.withdraw.Withdraw;
import com.banking_api.banking_api.dtos.DepositDTO;
import com.banking_api.banking_api.dtos.WithdrawDTO;
import com.banking_api.banking_api.repository.DepositRepository;
import com.banking_api.banking_api.repository.WithdrawRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WithdrawService {

    private final WithdrawRepository repository;
    private final AccountService accountService;

    public WithdrawService(WithdrawRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }

    @Transactional
    public Withdraw withdraw (WithdrawDTO dto) throws Exception {
        var account = accountService.findByAccountId(dto.accountId());
        var value = dto.value();

        if (account.getBalance().compareTo(value) < 0) {
            throw new Exception("Saldo insuficiente para realizar o saque");
        }

        var newBalance = account.getBalance().subtract(value);
        account.setBalance(newBalance);
        accountService.save(account);

        Withdraw newWithdraw = new Withdraw();
        newWithdraw.setValue(value);
        newWithdraw.setTimestamp(LocalDateTime.now());
        newWithdraw.setAccount(account);

        repository.save(newWithdraw);
        return newWithdraw;
    }

}
