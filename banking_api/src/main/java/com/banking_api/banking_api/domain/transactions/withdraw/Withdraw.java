package com.banking_api.banking_api.domain.transactions.withdraw;
import com.banking_api.banking_api.domain.account.Account;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
public class Withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private BigDecimal value;
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}


