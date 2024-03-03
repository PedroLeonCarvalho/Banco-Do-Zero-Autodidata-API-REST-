package com.banking_api.banking_api.repository;

import com.banking_api.banking_api.domain.account.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository <Account , Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.active = false WHERE a.id = :id")
    void deactivateAccountById(@Param("id") Long id);

}
