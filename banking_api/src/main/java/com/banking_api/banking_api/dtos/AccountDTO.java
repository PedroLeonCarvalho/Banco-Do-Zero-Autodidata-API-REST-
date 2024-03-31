package com.banking_api.banking_api.dtos;

import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record AccountDTO(
        @NotBlank(message = "O número da conta não pode estar em branco")
        @Size(min = 4, max = 20, message = "O número da conta deve ter entre 4 e 20 caracteres")
        String accountNumber,

        @NotNull(message = "O saldo não pode ser nulo")
        @Positive(message = "O saldo deve ser um valor positivo")
        BigDecimal balance,

        @NotNull(message = "O tipo de conta não pode ser nulo")
        AccountType type,

        @NotNull(message = "A data de criação não pode ser nula")
        LocalDate creationDate,

        LocalDateTime lastDepositDate,

        boolean active,

        @NotNull(message = "O id do usuário não pode ser nulo")
        Long user
) {}
