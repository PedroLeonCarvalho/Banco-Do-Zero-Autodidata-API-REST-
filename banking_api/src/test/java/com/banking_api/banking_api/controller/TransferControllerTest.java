package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.domain.account.Earnings;
import com.banking_api.banking_api.domain.transactions.transfer.Transfer;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.TransferDTO;
import com.banking_api.banking_api.dtos.WithdrawDTO;
import com.banking_api.banking_api.repository.AccountRepository;
import com.banking_api.banking_api.repository.UserRepository;
import lombok.AllArgsConstructor;
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

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
@ActiveProfiles("test")
@Transactional
@Rollback

class TransferControllerTest {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  AccountRepository accountRepository;
    @Autowired
    private  JacksonTester<TransferDTO> jacksonTester;
    @Autowired
    private  MockMvc mvc;

    Account senderAccount = new Account();
    Account receiverAccount = new Account();

    @BeforeEach
    public void setup() {
        User senderUser = new User();
        senderUser.setName("name");
        senderUser.setEmail("email@com");
        senderUser.setCpf("123123");
        senderUser.setBirthDate(LocalDate.of(1990, 6, 30));
        senderUser.setAge(33);
        senderUser.setCity("cidade");
        senderUser.setActive(true);
        senderUser.setUsername("username");
        senderUser.setPassword("password");
        userRepository.save(senderUser);
        senderAccount.setAccountNumber("12345678902");
        senderAccount.setBalance(new BigDecimal("1000.00"));
        senderAccount.setType(AccountType.POUPANCA);
        senderAccount.setCreationDate(LocalDate.of(2024, 3, 17));
        senderAccount.setLastDepositDate(LocalDateTime.of(2024, 3, 17, 12, 0));
        senderAccount.setActive(true);
        senderAccount.setUser(senderUser);
        accountRepository.save(senderAccount);

        Earnings senderEarnings = new Earnings();

        senderEarnings.setEarningsAmount(new BigDecimal(0.01));
        senderEarnings.setEarningsDate(LocalDate.now());
        receiverAccount.setEarnings(senderEarnings);
        accountRepository.save(senderAccount);


        User receiverUser = new User();
        receiverUser.setName("name2");
        receiverUser.setEmail("email@com2");
        receiverUser.setCpf("1231232");
        receiverUser.setBirthDate(LocalDate.of(1990, 6, 30));
        receiverUser.setAge(33);
        receiverUser.setCity("cidade");
        receiverUser.setActive(true);
        receiverUser.setUsername("username2");
        receiverUser.setPassword("password2");
        userRepository.save(receiverUser);


        receiverAccount.setAccountNumber("123456789022");
        receiverAccount.setBalance(new BigDecimal("1000.00"));
        receiverAccount.setType(AccountType.POUPANCA);
        receiverAccount.setCreationDate(LocalDate.of(2024, 3, 17));
        receiverAccount.setLastDepositDate(LocalDateTime.of(2024, 3, 17, 12, 0));
        receiverAccount.setActive(true);
        receiverAccount.setUser(receiverUser);
        accountRepository.save(receiverAccount);

        Earnings receiverEarnings = new Earnings();
        receiverEarnings.setEarningsAmount(new BigDecimal(0.01));
        receiverEarnings.setEarningsDate(LocalDate.now());
        receiverAccount.setEarnings(receiverEarnings);
        accountRepository.save(receiverAccount);
    }

    @Test
    @DisplayName("Should return 200 ok and some information")
    public void transferTest1() throws Exception {
        //given

        var senderId = senderAccount.getId();
        var receiverId = receiverAccount.getId();
        var value = new BigDecimal(100);

        var responseDTO = TransferDTO.builder()
                .timestamp(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                .value(value)
                .receiverId(receiverId)
                .build();

        RequestPostProcessor postProcessor = SecurityMockMvcRequestPostProcessors.user("username").roles("USER");

        //when
        var response = mvc.perform(post("/transfer")
                //Acrescenta o usuário logado
                .with(postProcessor)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(
                                TransferDTO.builder()
                                        .senderId(senderId)
                                        .receiverId(receiverId)
                                        .value(value).build())
                        .getJson())
        ).andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());


        var jsonExpected = jacksonTester.write(
                        responseDTO)
                .getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonExpected);
    }

    @Test
    @DisplayName("Should return 402 Insuficient Balance")
    public void transferTest2() throws Exception {
        //given

        var senderId = senderAccount.getId();
        var receiverId = receiverAccount.getId();
        var value = new BigDecimal(2000);



        RequestPostProcessor postProcessor = SecurityMockMvcRequestPostProcessors.user("username").roles("USER");

        //when
        var response = mvc.perform(post("/transfer")
                //Acrescenta o usuário logado
                .with(postProcessor)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(
                                TransferDTO.builder()
                                        .senderId(senderId)
                                        .receiverId(receiverId)
                                        .value(value).build())
                        .getJson())
        ).andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.PAYMENT_REQUIRED.value());


    }

    @Test
    @DisplayName("Should return 404 not found")
    public void transferTest3() throws Exception {
        //given

        var senderId = senderAccount.getId();
        var receiverId = 99L;
        var value = new BigDecimal(100);



        RequestPostProcessor postProcessor = SecurityMockMvcRequestPostProcessors.user("username").roles("USER");

        //when
        var response = mvc.perform(post("/transfer")
                //Acrescenta o usuário logado
                .with(postProcessor)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(
                                TransferDTO.builder()
                                        .senderId(senderId)
                                        .receiverId(receiverId)
                                        .value(value).build())
                        .getJson())
        ).andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());


    }
}