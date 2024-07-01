package com.banking_api.banking_api.domain.transactions.transfer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transfers" )

public class Transfer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(name = "sender_id")
        private Long senderId;
        @Column(name = "receiver_id")
        private Long receiverId;
        @Column(name = "transfer_value")
        private BigDecimal value;
        @Column(name = "transfer_timestamp")
        private LocalDateTime timestamp;
    }
