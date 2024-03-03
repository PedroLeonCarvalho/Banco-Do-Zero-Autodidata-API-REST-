package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.AccountDTO;
import com.banking_api.banking_api.dtos.AccountDeleteDto;
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


    public void delete(AccountDeleteDto id) throws Exception {
        if (!repository.existsById(id.id())) {
            throw new Exception("Conta não existe");
        } else {
            repository.deactivateAccountById(id.id());
        }
    }
}