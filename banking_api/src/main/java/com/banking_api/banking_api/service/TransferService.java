package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.transactions.transfer.Transfer;
import com.banking_api.banking_api.dtos.TransferDTO;
import com.banking_api.banking_api.infra.exception.InsufficientBalanceException;
import com.banking_api.banking_api.infra.exception.UnauthorizedUserException;
import com.banking_api.banking_api.repository.TransferRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class TransferService {

    private final TransferRepository repository;
    private final AccountService accountService;


    public TransferService(TransferRepository repository, AccountService accountService) {
        this.repository = repository;
        this.accountService = accountService;

    }

    public TransferDTO transfer(TransferDTO dto, String username) throws EntityNotFoundException, InsufficientBalanceException, UnauthorizedUserException {
        var sender = accountService.findByAccountId(dto.getSenderId());
        var receiver = accountService.findByAccountId(dto.getReceiverId());
        var value = dto.getValue();

        if (!sender.getUser().getUsername().equals(username)){
            throw new UnauthorizedUserException("Usuário nao autorizado");
        }
        if (receiver.getUser()==null){
            throw new EntityNotFoundException("Usuário de destino não localizado");
        }

        if (sender.getBalance().compareTo(value) < 0) {
            throw new InsufficientBalanceException ("Saldo insuficiente para realizar a operação.");
        }

        var newSenderBalance = sender.getBalance().subtract(value);
        sender.setBalance(newSenderBalance);
        accountService.save(sender);

        var newReceiverBalance = receiver.getBalance().add(value);
        receiver.setBalance(newReceiverBalance);
        accountService.save(receiver);

        Transfer transfer = new Transfer();
        transfer.setValue(dto.getValue());
        transfer.setTimestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        transfer.setSenderId(dto.getSenderId());
        transfer.setReceiverId(dto.getReceiverId());

        repository.save(transfer);


        return TransferDTO.builder()
                .timestamp(transfer.getTimestamp())
                .value(transfer.getValue())
                .receiverId(transfer.getReceiverId())
                .build();
    }
}
