package com.banking_api.banking_api.domain.transactions.withdraw;
import com.banking_api.banking_api.domain.account.Account;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;


import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "withdrawls")
public class Withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "withdrawl_value")
    private BigDecimal value;
    @Column(name = "withdrawl_timestamp")
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}


