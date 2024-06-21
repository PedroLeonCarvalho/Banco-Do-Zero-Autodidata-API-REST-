package com.banking_api.banking_api.repository;

import com.banking_api.banking_api.domain.transactions.deposit.Deposit;
import com.banking_api.banking_api.domain.transactions.withdraw.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface WithdrawRepository extends JpaRepository <Withdraw, Long> {

}

