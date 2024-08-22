package com.banking_api.banking_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountDeleteDto (
        @NotNull
        Long id
) {
}
