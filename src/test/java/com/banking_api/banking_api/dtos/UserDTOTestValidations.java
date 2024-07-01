package com.banking_api.banking_api.dtos;


import com.banking_api.banking_api.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDTOTestValidations {
    private static Validator validator;
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    static void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testUserValidation() {
        //  Valores que violam várias regras de validação
        UserDto user = new UserDto(
                1L,
                "",      // nome vazio e curto
                "email", // formato de e-mail inválido
                "123",   // CPF com menos de 11 dígitos
                LocalDate.now().plusDays(1), // data de nascimento no futuro
                11,       // idade menor que 13
                "",      // cidade vazia
                "",  // nome de usuário muito curto & vazio
                ""       // senha vazia
        );

        // Valida o objeto User
        Set<ConstraintViolation<UserDto>> violations = validator.validate(user);

        //Opções 1, 2 e 3
//        // (1) Verifica se há violações
//        assertFalse(violations.isEmpty(), "O objeto user tem violações de validação");
//
//        // (2) Ou verifica o número específico de violações
//        assertEquals(10, violations.size(), "Número de violações de validação");

        // (3) Ou você pode verificar violações específicas, se necessário
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")), "Campo 'name' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")), "Campo 'email' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cpf")), "Campo 'cpf' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("birthDate")), "Campo 'birthDate' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("age")), "Campo 'age' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("city")), "Campo 'city' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")), "Campo 'username' deve ser inválido");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")), "Campo 'password' deve ser inválido");
    }
}

