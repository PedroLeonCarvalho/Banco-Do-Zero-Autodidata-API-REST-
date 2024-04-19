package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.domain.account.Earnings;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.AccountDTO;
import com.banking_api.banking_api.dtos.AccountDeleteDto;
import com.banking_api.banking_api.dtos.SelicDTO;
import com.banking_api.banking_api.infra.exception.BadResponseException;
import com.banking_api.banking_api.repository.AccountRepository;
import com.banking_api.banking_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {
    @Mock
    private AccountRepository repository;
    @Mock
    private UserService userService;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DepositService depositService;

    @InjectMocks
    private AccountService accountService;

    Account account = new Account();
    List accounts = new ArrayList<>();
    Optional<List<Account>> accountsOptional = Optional.of(accounts);

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        var dataInicial = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        var dataFinal = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        SelicDTO selicDTO = new SelicDTO();
        selicDTO.setValor(new BigDecimal("0.08"));
        SelicDTO[] arrayComUmElemento = { selicDTO };

        String url = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados?formato=json&dataInicial=" + dataInicial + "&dataFinal=" + dataFinal;
        Mockito
                .when(restTemplate.getForEntity(
                        url, SelicDTO[].class))
                .thenReturn(new ResponseEntity(arrayComUmElemento, HttpStatus.OK));

        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setEmail("email@com");
        user.setCpf("123123");
        user.setBirthDate(LocalDate.of(1990, 6, 30));
        user.setAge(33);
        user.setCity("cidade");
        user.setActive(true);
        user.setUsername("username");
        user.setPassword("password");
        userRepository.save(user);

        account.setId(1L);
        account.setAccountNumber("12345678902");
        account.setBalance(new BigDecimal("1000.00"));
        account.setType(AccountType.POUPANCA);
        account.setCreationDate(LocalDate.of(2024, 3, 17));
        account.setLastDepositDate(LocalDateTime.of(2023, 3, 17, 12, 0));
        account.setActive(true);
        account.setUser(user);
        repository.save(account);

        Earnings earnings = new Earnings();

        earnings.setEarningsAmount(new BigDecimal(0.01));
        earnings.setEarningsDate(LocalDate.now());
        account.setEarnings(earnings);
        repository.save(account);
        accounts.add(account);

    }


    @Test
    void whenTestEarningsGenerate_thenReturnSucess() throws JSONException, BadResponseException {
        when(repository.findOptionalAccountsActiveAndPoupanca()).thenReturn(accountsOptional);

        accountService.earningsGenerate();
        verify(repository, times(accounts.size())).findOptionalAccountsActiveAndPoupanca();
    }

    @Test
    @DisplayName("Quando não há contas localizadas retona Not Found 404")
    void whenEarningsGenerate_thenThrowException() {
        when(repository.findAccountsActiveAndPoupanca()).thenReturn(null);
        var ex = assertThrows(EntityNotFoundException.class, () -> {
            accountService.earningsGenerate();
        });
        assertEquals("Conta não encontrada com o ID", ex.getMessage());

    }

    @Test
    @DisplayName("Both tests updateBalanceWithEarnings and calculateBalancePlusEarnings are tested here. " +
            "The final account balance is rightly calculated after have its earnings")
    void whenUpdateBalanceWithEarnings_thenResponseHasTheRightValueAdded() throws JSONException, BadResponseException {


        var expectedNewBalance = BigDecimal.valueOf(1085);

        accountService.updateBalanceWithEarnings(account);
        verify(repository, times(3)).save(account);
        assertEquals(expectedNewBalance, account.getBalance().setScale(0));
    }


    @Test
    void whenCreateAccount_then202Created() {
        AccountDTO dto = new AccountDTO("12345678902", new BigDecimal("1000.00"), AccountType.POUPANCA, LocalDate.now(), LocalDateTime.of(2023, 3, 17, 12, 0), true, 1L);
        when(userService.findUserById(any())).thenReturn(new User());
        accountService.createAccount(dto);
         verify(repository, times(2)).save(account);
    }

    @Test
    void delete_200OK() {
        AccountDeleteDto dto = new AccountDeleteDto(account.getId());
        when(repository.existsById(any())).thenReturn(true);
        accountService.delete(dto);
        verify(repository, times(1)).existsById(dto.id());
        verify(repository, times(1)).deactivateAccountById(dto.id());

    }
    @Test
    void whenDelete_thenEntityNotFoundException() {
        AccountDeleteDto dto = new AccountDeleteDto(account.getId());
        when(repository.existsById(any())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> {accountService.delete(dto);},"Conta não existe");
    }


}
