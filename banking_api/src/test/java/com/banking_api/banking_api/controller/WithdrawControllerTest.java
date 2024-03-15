package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.dtos.WithdrawDTO;
import com.banking_api.banking_api.service.WithdrawService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
class WithdrawControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<WithdrawDTO> jacksonTester;

    @MockBean
    private WithdrawService withdrawService;

    @Test
    @DisplayName("Should return 200 when there is enough balance")
    void withdraw() throws Exception {
        // Given
        var timestamp = LocalDateTime.now();
        var value = new BigDecimal(100);
var responseDTO = new WithdrawDTO(null, null, null, timestamp, null, value);
        when(withdrawService.withdraw(any())).thenReturn(responseDTO);

        // When
        var response = mvc.perform(post("/withdraw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTester.write(
                                new WithdrawDTO(null, null, value, timestamp, null, null))
                        .getJson())
        ).andReturn().getResponse();

        // Then
         assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonExpected = jacksonTester.write(
                        responseDTO)
                .getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonExpected);
    }
}
