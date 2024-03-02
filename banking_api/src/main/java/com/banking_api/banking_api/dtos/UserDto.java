package com.banking_api.banking_api.dtos;

import lombok.NoArgsConstructor;

import java.util.Date;

public record UserDto(
        Long id,
        String name,
        String email,
        String cpf,
        Date birthDate,
        int age,
        String city,
        String username,
        String password

) { }
