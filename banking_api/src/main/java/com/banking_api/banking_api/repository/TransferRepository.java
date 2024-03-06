package com.banking_api.banking_api.repository;

import com.banking_api.banking_api.domain.transactions.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
