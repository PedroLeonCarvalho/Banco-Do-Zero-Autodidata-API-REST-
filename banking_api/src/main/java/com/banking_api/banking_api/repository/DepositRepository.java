package com.banking_api.banking_api.repository;

import com.banking_api.banking_api.domain.transactions.deposit.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepositRepository extends JpaRepository <Deposit, Long> {


    @Query("SELECT d FROM Deposit d ORDER BY d.timestamp DESC")
    Deposit getLastDepositDate();
}
