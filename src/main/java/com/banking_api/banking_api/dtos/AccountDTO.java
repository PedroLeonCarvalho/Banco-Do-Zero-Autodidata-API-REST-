package com.banking_api.banking_api.dtos;

import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.domain.user.User;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Fetch;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record AccountDTO(
        @NotBlank(message = "O número da conta não pode estar em branco")
        @Size(min = 4, max = 20, message = "O número da conta deve ter entre 4 e 20 caracteres")
        String accountNumber,
        @PositiveOrZero
        BigDecimal balance,

        @NotNull(message = "O tipo de conta não pode ser nulo")
        AccountType type,
         @NotNull(message = "Data de criação da conta não pode ser nulo")
        LocalDate creationDate,

        LocalDateTime lastDepositDate,

        boolean active,

        @NotNull(message = "O id do usuário não pode ser nulo")
        Long user
) {}
