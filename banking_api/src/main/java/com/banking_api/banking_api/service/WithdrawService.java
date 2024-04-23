package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.transactions.deposit.Deposit;
import com.banking_api.banking_api.domain.transactions.withdraw.Withdraw;
import com.banking_api.banking_api.dtos.DepositDTO;
import com.banking_api.banking_api.dtos.WithdrawDTO;
import com.banking_api.banking_api.dtos.WithdrawResponseDTO;
import com.banking_api.banking_api.infra.exception.InsufficientBalanceException;
import com.banking_api.banking_api.infra.exception.UnauthorizedUserException;
import com.banking_api.banking_api.repository.DepositRepository;
import com.banking_api.banking_api.repository.WithdrawRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class WithdrawService {

    private final WithdrawRepository repository;
    private final AccountService accountService;

    public WithdrawService(WithdrawRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;
    }


    @Transactional
    public WithdrawResponseDTO withdraw (WithdrawDTO dto, String username) throws EntityNotFoundException, InsufficientBalanceException, UnauthorizedUserException {
        var account = accountService.findByAccountId(dto.getAccountId());
        var value = dto.getValue();

        if(!account.getUser().getUsername().equals(username)) {
            throw new UnauthorizedUserException("Usuário não autorizado");
        }


        if (account.getBalance().compareTo(value) < 0) {
            throw new InsufficientBalanceException ("Saldo insuficiente para realizar a operação.");
        }

        var newBalance = account.getBalance().subtract(value);
        account.setBalance(newBalance);
        accountService.save(account);

        Withdraw newWithdraw = new Withdraw();
        newWithdraw.setValue(value);
        newWithdraw.setTimestamp(LocalDateTime.now());
        newWithdraw.setAccount(account);
        repository.save(newWithdraw);




        return  WithdrawResponseDTO.builder().timestamp(newWithdraw.getTimestamp().truncatedTo(ChronoUnit.MINUTES)).newBalance(newBalance).build();
    }



}
