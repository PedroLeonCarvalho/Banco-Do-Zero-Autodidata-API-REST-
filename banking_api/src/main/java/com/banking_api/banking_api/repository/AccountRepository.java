package com.banking_api.banking_api.repository;

import com.banking_api.banking_api.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <Account , Long> {



}
