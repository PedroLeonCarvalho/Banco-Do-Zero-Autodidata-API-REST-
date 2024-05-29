package com.banking_api.banking_api.domain.account;

import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.infra.exception.BadResponseException;
import com.banking_api.banking_api.service.IEarningsRateStrategy;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Table(name = "accounts")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
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
