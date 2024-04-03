package com.banking_api.banking_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record AccountDeleteDto (
        @NotBlank
        Long id
) {
}
