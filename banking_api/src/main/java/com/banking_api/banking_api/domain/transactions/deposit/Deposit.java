package com.banking_api.banking_api.domain.transactions.deposit;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.dtos.DepositDTO;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter

public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private BigDecimal value;
    private LocalDateTime timestamp;

    public Deposit(DepositDTO dto) {
        this.account = new Account(dto.accountId());
        this.value = dto.value();
    }
}
