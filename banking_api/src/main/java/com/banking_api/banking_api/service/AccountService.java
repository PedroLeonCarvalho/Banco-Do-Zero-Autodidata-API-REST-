package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.AccountDTO;
import com.banking_api.banking_api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;

        public Account createAccount(AccountDTO dto) {
            Account account = new Account(dto);
            repository.save(account);
        return account;
        }

}
