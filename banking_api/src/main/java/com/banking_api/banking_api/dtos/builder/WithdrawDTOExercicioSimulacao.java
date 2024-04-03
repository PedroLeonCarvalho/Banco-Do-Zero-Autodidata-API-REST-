package com.banking_api.banking_api.dtos.builder;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class WithdrawDTOExercicioSimulacao {
    @NotNull(message = "O ID não pode ser nulo")
    private Long id;

    @NotNull(message = "O ID da conta não pode ser nulo")
    private Long accountId;

    @NotNull(message = "O valor não pode ser nulo")
    @Positive(message = "O valor deve ser positivo")
    private BigDecimal value;

    @NotNull(message = "O timestamp não pode ser nulo")
    @Past(message = "O timestamp deve estar no passado")
    private LocalDateTime timestamp;

    @NotNull(message = "O ID da conta não pode ser nulo")
    private Long account;

    @NotNull(message = "O novo saldo não pode ser nulo")
    @Positive(message = "O novo saldo deve ser positivo")
    private BigDecimal newBalance;}


