package com.banking_api.banking_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NoArgsConstructor;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDto(
        Long id,

        String name,
        String email,
        String cpf,
        Date birthDate,
        Integer age,
        String city,
        String username,
        String password

) {

}
