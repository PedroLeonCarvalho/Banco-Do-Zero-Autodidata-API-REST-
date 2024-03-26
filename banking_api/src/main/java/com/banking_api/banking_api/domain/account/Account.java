package com.banking_api.banking_api.domain.account;

import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.AccountDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@ToString

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number")
    private String accountNumber;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "last_deposit_date")
    private LocalDateTime lastDepositDate;

    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Embedded
    private Earnings earnings;


    public Account(Long aLong) {
        this.id = id;
    }
}
