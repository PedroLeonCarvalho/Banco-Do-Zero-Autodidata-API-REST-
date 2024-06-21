package com.banking_api.banking_api.domain.transactions.deposit;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.dtos.DepositDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "deposits")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "deposit_value")
    private BigDecimal value;

    @Column(name = "time_stamp")
    private LocalDateTime timestamp;

    public Deposit(DepositDTO dto) {
        this.account = new Account(dto.getAccountId());
        this.value = dto.getValue();
    }
}
