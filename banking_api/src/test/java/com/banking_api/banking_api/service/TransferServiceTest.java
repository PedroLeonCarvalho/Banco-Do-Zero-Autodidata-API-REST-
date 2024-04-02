package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.transactions.transfer.Transfer;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.TransferDTO;
import com.banking_api.banking_api.infra.exception.InsufficientBalanceException;
import com.banking_api.banking_api.infra.exception.UnauthorizedUserException;
import com.banking_api.banking_api.repository.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferServiceTest {

    @Mock
    private AccountService accountService;
    @Mock
    private TransferRepository repository;

    @InjectMocks
    private TransferService transferService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    // #### Levei 50 minutos pra escrever esse teste na mão
    @Test
    void transfer() throws UnauthorizedUserException, InsufficientBalanceException {
        var dto = TransferDTO.builder()
                .receiverId(1L)
                .senderId(2L)
                .value(new BigDecimal(100))
                .build();

        Transfer transfer = new Transfer();
        transfer.setValue(dto.getValue());
        transfer.setTimestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        transfer.setSenderId(dto.getSenderId());
        transfer.setReceiverId(dto.getReceiverId());

        Account receiver = new Account();
        receiver.setId(1L);
        receiver.setBalance(new BigDecimal(100));
        receiver.setUser(new User());

        Account sender = new Account();
        sender.setUser(new User());
        sender.setId(2L);
        sender.getUser().setUsername("nome");
        var nome = sender.getUser().getUsername();
        sender.setBalance(new BigDecimal(1000));

        when(accountService.findByAccountId(2L)).thenReturn(sender);
        when(accountService.findByAccountId(1L)).thenReturn(receiver);

        transferService.transfer(dto,nome);


        verify(accountService, times(1)).save(sender);
        verify(accountService, times(1)).save(receiver);
        verify(repository, times(1)).save(any());


        assertEquals(1L,receiver.getId());

    }

    //#### COM ERRO : ChatGPT me entregou esse teste com erro em 3 minutos ( contando tempo de dar o contexto e corrigir nome de variáveis)
//    @Test
//    public void testTransfer_Success() throws UnauthorizedUserException, InsufficientBalanceException {
//        TransferDTO transferDTO = TransferDTO.builder()
//                .senderId(1L)
//                .receiverId(2L)
//                .value(BigDecimal.valueOf(100))
//                .build();
//
//        Account sender = new Account();
//        sender.setId(1L);
//        sender.setBalance(BigDecimal.valueOf(200));
//
//        Account receiver = new Account();
//        receiver.setId(2L);
//        receiver.setBalance(BigDecimal.valueOf(50));
//
//        when(accountService.findByAccountId(1L)).thenReturn(sender);
//        when(accountService.findByAccountId(2L)).thenReturn(receiver);
//
//        Transfer transfer = new Transfer();
//        transfer.setValue(BigDecimal.valueOf(100));
//        transfer.setSenderId(1L);
//        transfer.setReceiverId(2L);
//
//        when(repository.save(any(Transfer.class))).thenReturn(transfer);
//
//        TransferDTO result = transferService.transfer(transferDTO, "username");
//
//        assertEquals(BigDecimal.valueOf(100), result.getValue());
//        assertEquals(2L, result.getReceiverId());
//        // Add more assertions as needed
//    }
//
//


    //### CORRETO 13 minutos desde o chatGPT gerar até te-lo corrigido
    @Test
    public void testTransfer_Success() throws UnauthorizedUserException, InsufficientBalanceException {
        TransferDTO transferDTO = TransferDTO.builder()
                .senderId(1L)
                .receiverId(2L)
                .value(BigDecimal.valueOf(100))
                .build();

        Account sender = new Account();
        sender.setId(1L);
        sender.setBalance(BigDecimal.valueOf(200));
        sender.setUser(new User());
        sender.getUser().setUsername("username");

        Account receiver = new Account();
        receiver.setId(2L);
        receiver.setBalance(BigDecimal.valueOf(50));
        receiver.setUser(new User());

        when(accountService.findByAccountId(1L)).thenReturn(sender);
        when(accountService.findByAccountId(2L)).thenReturn(receiver);

        Transfer transfer = new Transfer();
        transfer.setValue(BigDecimal.valueOf(100));
        transfer.setSenderId(1L);
        transfer.setReceiverId(2L);

        when(repository.save(eq(transfer))).thenReturn(transfer);

        TransferDTO result = transferService.transfer(transferDTO, "username");

        assertEquals(BigDecimal.valueOf(100), result.getValue());
        assertEquals(2L, result.getReceiverId());
        // Add more assertions as needed
    }

}