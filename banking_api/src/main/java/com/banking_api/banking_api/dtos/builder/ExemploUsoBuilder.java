package com.banking_api.banking_api.dtos.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExemploUsoBuilder {

   WithdrawDTOExercicioSimulacao dto = new WithdrawDTOBuilder()
           .buildId(1L)
           .buildAccount(2L)
           .buildValue(BigDecimal.valueOf(100))
           .buildTimestamp(LocalDateTime.now())
           .buildAccountId(3L)
           .buildNewBalance(BigDecimal.valueOf(500))
           .build();

}
