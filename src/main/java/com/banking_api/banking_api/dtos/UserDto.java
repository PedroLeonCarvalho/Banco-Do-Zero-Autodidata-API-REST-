package com.banking_api.banking_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)

public record UserDto(

        Long id,
        @NotBlank(message = "name is mandatory")
        @Size(min = 2, max = 200)
        String name,

        @NotBlank(message = "email is mandatory")
        @Email
        String email,
        @NotBlank(message = "cpf is mandatory")
                @Size(min =11)
        String cpf,
        @NotNull(message = "birthDate is mandatory")
        @Past
        LocalDate birthDate,
        @NotNull(message = "age is mandatory")
        @Min(13)
        Integer age,
        @NotBlank(message = "city is mandatory")
        String city,
        @NotBlank(message = "username is mandatory")
        @Size(min = 2, max = 20)
        String username,
        @NotBlank(message = "password is mandatory")
        String password
) implements Serializable {
}


