package com.banking_api.banking_api.dtos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountDTOValidationsTest {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testAccountDTOValidation() {
        // Valores que violam várias regras de validação
        AccountDTO accountDTO = new AccountDTO(
                "",                  // Número da conta vazio
                BigDecimal.valueOf(-100), // Saldo negativo
                null,                // Tipo de conta nulo
                null,                // Data de criação nula
                LocalDateTime.now(), // Data do último depósito atual
                true,                // Active = true
                null                 // ID do usuário nulo
        );

        // Valida o objeto AccountDTO
        Set<ConstraintViolation<AccountDTO>> violations = validator.validate(accountDTO);

        // Verifica se há violações de validação
        assertFalse(violations.isEmpty(), "O objeto AccountDTO deve ser inválido");

        // Verifica violações específicas, se necessário
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("accountNumber")), "Campo 'accountNumber' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("balance")), "Campo 'balance' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("type")), "Campo 'type' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("creationDate")), "Campo 'creationDate' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("user")), "Campo 'user' deve ser inválido");
    }
}
