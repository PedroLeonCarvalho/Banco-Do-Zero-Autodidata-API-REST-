package com.banking_api.banking_api.dtos;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WithdrawDTOTestValidation {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

    }

    @Test
    void testWithdrawDTOValidation() {
        // Valores que violam várias regras de validação
        WithdrawDTO withdrawDTO = WithdrawDTO.builder()

                .accountId(null) // ID da conta nulo
                .value(null) // Valor nulo
                .build();

        // Valida o objeto WithdrawDTO
        Set<ConstraintViolation<WithdrawDTO>> violations = validator.validate(withdrawDTO);

        // Verifica se há violações de validação
        assertFalse(violations.isEmpty(), "O objeto WithdrawDTO deve ser inválido");

        // Verifica violações específicas, se necessário
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("accountId")), "Campo 'accountId' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("value")), "Campo 'value' deve ser inválido");

    }
}

