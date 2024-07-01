package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.transactions.deposit.Deposit;
import com.banking_api.banking_api.dtos.DepositDTO;
import com.banking_api.banking_api.repository.DepositRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DepositService {

    private final DepositRepository repository;
    private final AccountService accountService;
    private final EmailService emailService;


    public DepositService(DepositRepository repository, AccountService accountService, EmailService emailService) {
        this.repository = repository;
        this.accountService = accountService;

        this.emailService = emailService;
    }

    @Transactional
    public DepositDTO deposit (DepositDTO dto) throws EntityNotFoundException {

        var account = accountService.findByAccountId(dto.getAccountId());
        if (account == null) { throw  new EntityNotFoundException("Conta não localizada");}
        else {

        var value = dto.getValue();

        var newBalance = account.getBalance().add(value);
        account.setBalance(newBalance);
        Deposit newDeposit = new Deposit();
        newDeposit.setValue(value);
        newDeposit.setTimestamp(LocalDateTime.now());
        newDeposit.setAccount(account);
        account.setLastDepositDate(newDeposit.getTimestamp());
        accountService.save(account);
        repository.save(newDeposit);
        emailService.sendEmail(value, newDeposit.getTimestamp());



        return DepositDTO.builder().value(value).newBalance(newBalance).build();
        }
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
