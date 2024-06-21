package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.transactions.withdraw.Withdraw;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.WithdrawDTO;
import com.banking_api.banking_api.dtos.WithdrawResponseDTO;
import com.banking_api.banking_api.infra.exception.InsufficientBalanceException;
import com.banking_api.banking_api.infra.exception.UnauthorizedUserException;
import com.banking_api.banking_api.repository.WithdrawRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WithdrawServiceTest {

    @Mock
    private WithdrawRepository withdrawRepository;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private WithdrawService withdrawService;

    private WithdrawDTO validWithdrawDto;

    private Account validAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);


        validWithdrawDto = WithdrawDTO.builder()
                .accountId(1L)
                .value(new BigDecimal(25))
                .build();


        validAccount = new Account();
        validAccount.setId(1L);
        validAccount.setBalance(BigDecimal.valueOf(1000));
        validAccount.setUser(new User());
        validAccount.getUser().setUsername("username");
    }

    @Test
    void withdraw_Successful() throws InsufficientBalanceException, UnauthorizedUserException {
        // Arrange
        when(accountService.findByAccountId(validWithdrawDto.getAccountId())).thenReturn(validAccount);

        // Act
        WithdrawResponseDTO result = withdrawService.withdraw(validWithdrawDto, "username");

        // Assert
        assertNotNull(result);
        assertEquals(validAccount.getBalance(), result.getNewBalance());
        verify(accountService, times(1)).save(validAccount);
        verify(withdrawRepository, times(1)).save(any(Withdraw.class));
    }

    @Test
    void withdraw_InsufficientBalanceException() throws InsufficientBalanceException, UnauthorizedUserException {
        // Arrange
        validWithdrawDto.setValue(BigDecimal.valueOf(2000));
        when(accountService.findByAccountId(validWithdrawDto.getAccountId())).thenReturn(validAccount);

        // Act and Assert
        assertThrows(InsufficientBalanceException.class, () -> withdrawService.withdraw(validWithdrawDto, "username"));
        verify(accountService, never()).save(any(Account.class));
        verify(withdrawRepository, never()).save(any(Withdraw.class));
    }

    @Test
    void withdraw_UnauthorizedUserException() throws InsufficientBalanceException, UnauthorizedUserException {
        // Arrange
        when(accountService.findByAccountId(validWithdrawDto.getAccountId())).thenReturn(validAccount);

        // Act and Assert
        assertThrows(UnauthorizedUserException.class, () -> withdrawService.withdraw(validWithdrawDto, "wrong_username"));
        verify(accountService, never()).save(any(Account.class));
        verify(withdrawRepository, never()).save(any(Withdraw.class));
    }
}

