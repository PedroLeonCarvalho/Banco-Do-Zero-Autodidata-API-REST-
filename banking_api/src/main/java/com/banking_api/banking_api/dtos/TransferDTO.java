package com.banking_api.banking_api.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferDTO(
        Long id,
        Long senderId,
        Long receiverId,
        BigDecimal value,
        LocalDateTime timestamp
                ) {
}
