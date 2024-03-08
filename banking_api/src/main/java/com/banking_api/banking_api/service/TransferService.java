package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.transactions.transfer.Transfer;
import com.banking_api.banking_api.dtos.TransferDTO;
import com.banking_api.banking_api.infra.exception.InsufficientBalanceException;
import com.banking_api.banking_api.repository.TransferRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransferService {

    private final TransferRepository repository;
    private final AccountService accountService;


    public TransferService(TransferRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;

    }

    public Transfer transfer(TransferDTO dto) throws EntityNotFoundException, InsufficientBalanceException {
        var sender = accountService.findByAccountId(dto.senderId());
        var receiver = accountService.findByAccountId(dto.receiverId());
        var value = dto.value();

        if (sender.getBalance().compareTo(value) < 0) {
            throw new InsufficientBalanceException("Saldo insuficiente para realizar a operação.");
        }

        var newSenderBalance = sender.getBalance().subtract(value);
        sender.setBalance(newSenderBalance);
        accountService.save(sender);

        var newReceiverBalance = receiver.getBalance().add(value);
        receiver.setBalance(newReceiverBalance);
        accountService.save(receiver);

        Transfer transfer = new Transfer();
        transfer.setValue(dto.value());
        transfer.setTimestamp(LocalDateTime.now());
        transfer.setSenderId(dto.senderId());
        transfer.setReceiverId(dto.receiverId());

        repository.save(transfer);
        return transfer;
    }
}
