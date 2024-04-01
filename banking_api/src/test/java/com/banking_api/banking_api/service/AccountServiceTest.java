package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.domain.account.Earnings;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    @Mock
    private  AccountRepository repository;
    @Mock
    private  DepositService depositService;
    @Mock
    private  UserService userService;

    @InjectMocks
    private AccountService accountService;

    Account account = new Account();
    List accounts = new ArrayList<>();
    @BeforeEach
    void setup () {
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
            accounts.add(account);
    }


    @Test
    void whenTestEarningsGenerate_thenReturnSucess() {
    when(repository.findAccountsActiveAndPoupanca()).thenReturn(accounts);
    accountService.earningsGenerate();
    verify(repository, times(accounts.size())).findAccountsActiveAndPoupanca();
    }

    @Test
    void whenEarningsGenerate_thenThrowException() {
        when(repository.findAccountsActiveAndPoupanca()).thenReturn(null);
        var ex = assertThrows(EntityNotFoundException.class, () -> {
            accountService.earningsGenerate();
        });
        assertEquals("Não há contas Poupança ativas com rendimentos pendentes", ex.getMessage());

    }

    @Test
    @DisplayName("Both tests updateBalanceWithEarnings and calculateBalancePlusEarnings are tested here. " +
            "The final account balance is rightly calculated after have its earnings")
    void whenUpdateBalanceWithEarnings_thenResponseHasTheRightValueAdded() {
        var expectedNewBalance = BigDecimal.valueOf(1010);
        accountService.updateBalanceWithEarnings(account);
        verify(repository, times(2)).save(account);
        assertEquals(expectedNewBalance, account.getBalance().setScale(0));
    }


    @Test
    void whenCreateAccount_then202Created() {
    }

    @Test
    void delete() {
    }
}

