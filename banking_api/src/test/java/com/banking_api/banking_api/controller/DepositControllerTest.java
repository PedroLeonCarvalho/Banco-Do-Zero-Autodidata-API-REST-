package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.domain.account.Earnings;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.DepositDTO;
import com.banking_api.banking_api.dtos.WithdrawDTO;
import com.banking_api.banking_api.repository.AccountRepository;
import com.banking_api.banking_api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
@ActiveProfiles("test")
@Transactional
@Rollback
class DepositControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DepositDTO> jacksonTester;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;


    Account account = new Account();

    @BeforeEach
    public void setup() {
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
        userRepository.save(user);


        account.setAccountNumber("12345678902");
        account.setBalance(new BigDecimal("1000.00"));
        account.setType(AccountType.POUPANCA);
        account.setCreationDate(LocalDate.of(2024, 3, 17));
        account.setLastDepositDate(LocalDateTime.of(2023, 3, 17, 12, 0));
        account.setActive(true);
        account.setUser(user);
        accountRepository.save(account);

        Earnings earnings = new Earnings();

        earnings.setEarningsAmount(new BigDecimal(0.01));
        earnings.setEarningsDate(LocalDate.now());
        account.setEarnings(earnings);
        accountRepository.save(account);
    }

    @Test
    @DisplayName("Retorna 200 ok e JSON com informmações")
    void testDeposit_200ok() throws Exception {
        // Given


        var accountId = account.getId();
        var value = new BigDecimal(2000);
        var accountNewBalance = account.getBalance().add(value);
        var responseDTO = DepositDTO.builder().value(value).newBalance(accountNewBalance).build();
        var jsonExpected = jacksonTester.write(responseDTO).getJson();
        //Simula o usuário logado com esse username
        RequestPostProcessor postProcessor = SecurityMockMvcRequestPostProcessors.user("username").roles("USER");

        // When
        var response = mvc.perform(post("/deposit")
                .with(postProcessor)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(DepositDTO.builder().accountId(accountId).value(value).build()
                        )
                        .getJson())
        ).andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonExpected);
    }

    @Test
    @DisplayName("Retorna 404, conta não localizada")
    void testDeposit_AccountNotFound() throws Exception {
        // Given

        var accountId = 99L;
        var value = new BigDecimal(2000);

        //Simula o usuário logado com esse username
        RequestPostProcessor postProcessor = SecurityMockMvcRequestPostProcessors.user("username").roles("USER");

        // When
        var response = mvc.perform(post("/deposit")
                .with(postProcessor)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(DepositDTO.builder().accountId(accountId).value(value).build()
                        )
                        .getJson())
        ).andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

}