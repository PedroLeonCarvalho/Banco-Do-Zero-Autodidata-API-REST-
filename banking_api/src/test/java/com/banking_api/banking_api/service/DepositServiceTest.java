package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.domain.account.Earnings;
import com.banking_api.banking_api.domain.transactions.deposit.Deposit;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.DepositDTO;
import com.banking_api.banking_api.repository.AccountRepository;
import com.banking_api.banking_api.repository.DepositRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DepositServiceTest {

    @Mock
    private AccountRepository repository;
    @InjectMocks
    private DepositService depositService;
    @Mock
    private UserService userService;
    @Mock
    private DepositRepository depositRepository;

    @Mock
    private AccountService accountService;

    Account account = new Account();

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        User user = new User();
        user.setName("name");
        user.setEmail("email@com");
        user.setCpf("123123");
        user.setBirthDate(LocalDate.of(1990, 6, 30));
        user.setAge(33);
        user.setCity("cidade");
        user.setActive(true);
        user.setUsername("username");
        user.setPassword("password");
        user.setId(1L);

        account.setId(1L);
        account.setAccountNumber("12345678902");
        account.setBalance(new BigDecimal("1000.00"));
        account.setType(AccountType.POUPANCA);
        account.setCreationDate(LocalDate.of(2024, 3, 17));
        account.setLastDepositDate(LocalDateTime.of(2023, 3, 17, 12, 0));
        account.setActive(true);
        account.setUser(user);

        Earnings earnings = new Earnings();

        earnings.setEarningsAmount(new BigDecimal(0.01));
        earnings.setEarningsDate(LocalDate.now());
        account.setEarnings(earnings);
        repository.save(account);

    }


    @Test
    void WhenDeposit_then200ok() {
        var dto = DepositDTO.builder().accountId(account.getId()).value(new BigDecimal(100)).build();
        Deposit deposit = new Deposit();
        deposit.setValue(new BigDecimal(100));
        deposit.setTimestamp(LocalDateTime.now());
        deposit.setAccount(account);

        when(accountService.findByAccountId(any())).thenReturn(account);
        depositService.deposit(dto);


        verify(accountService, times(1)).save(account);
        verify(depositRepository, times(1)).save(any());

        assertEquals(new BigDecimal(1100), account.getBalance().setScale(0));

    }

    @Test
    void WhenthenAccountIdNotFound_thenEntityNotFound() {
        var dto = DepositDTO.builder().accountId(account.getId()).value(new BigDecimal(100)).build();
        Deposit deposit = new Deposit();
        deposit.setValue(new BigDecimal(100));
        deposit.setTimestamp(LocalDateTime.now());
        deposit.setAccount(account);

        when(accountService.findByAccountId(any())).thenReturn(null);


        verify(accountService, times(0)).save(account);
        verify(depositRepository, times(0)).save(any());

        assertThrows(EntityNotFoundException.class, () -> {
            depositService.deposit(dto);
        }, "Conta não localizada");

    }

    // aqui por exemplo só testo se o repository foi chamado dentro do método, por que a lógica de pegar o ultimo depósito está na Query, que não dá pra testar  com repository Mockado"
    @Test
    void getLastDepositDate() {
        var dto = DepositDTO.builder().accountId(account.getId()).value(new BigDecimal(100)).build();
        Deposit deposit = new Deposit();
        when(depositRepository.getLastDepositDate()).thenReturn(deposit);
        depositService.getLastDepositDate();
        verify(depositRepository, times(1)).getLastDepositDate();
    }



    }
