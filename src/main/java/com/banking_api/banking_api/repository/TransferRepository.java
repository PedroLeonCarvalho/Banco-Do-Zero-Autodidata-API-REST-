package com.banking_api.banking_api.repository;

import com.banking_api.banking_api.domain.transactions.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
