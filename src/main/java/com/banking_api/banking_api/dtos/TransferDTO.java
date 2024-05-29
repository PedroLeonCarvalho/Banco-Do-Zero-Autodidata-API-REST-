package com.banking_api.banking_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferDTO {

    private Long id;
    @NotNull (message = "O valor deste cmapo não pode ser nulo")
    private Long senderId;
    @NotNull(message = "O valor deste cmapo não pode ser nulo")
    private Long receiverId;
    @NotNull(message = "O valor deste cmapo não pode ser nulo")
    private BigDecimal value;
    private LocalDateTime timestamp;
}