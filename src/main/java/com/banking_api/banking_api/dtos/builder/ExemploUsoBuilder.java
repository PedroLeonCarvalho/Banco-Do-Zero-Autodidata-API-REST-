package com.banking_api.banking_api.dtos.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExemploUsoBuilder {

   WithdrawDTOExercicioSimulacao dto = new WithdrawDTOBuilder()
           .id(1L)
           .account(2L)
           .value(BigDecimal.valueOf(100))
           .timestamp(LocalDateTime.now())
           .accountId(3L)
           .newBalance(BigDecimal.valueOf(500))
           .build();

}
