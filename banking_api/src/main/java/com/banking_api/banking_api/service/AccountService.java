package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.dtos.*;
import com.banking_api.banking_api.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }
@Transactional
    public Account createAccount(AccountDTO dto) {
        Account account = new Account(dto);
        repository.save(account);
        return account;
    }

@Transactional
    public void delete(AccountDeleteDto id) throws Exception {
        if (!repository.existsById(id.id())) {
            throw new Exception("Conta não existe");
        } else {
            repository.deactivateAccountById(id.id());
        }
    }


    public Page<AccountListDTO> getAllActiveAccounts(Pageable page) {
        var accounts = repository.findAllByActiveTrue(page);
        return accounts.map(a -> new AccountListDTO(a.getAccountNumber(), a.getType(), a.isActive(), a.getUser().getName()));
    }

    public AccountDTO findById(Long id) throws Exception {
        var account = repository.findById(id).orElseThrow(() -> new Exception("Usuario nao encontrado"));

        return convertToAccountDTO(account);
    }

    private AccountDTO convertToAccountDTO(Account a) {
        return new AccountDTO(a.getAccountNumber(),a.getBalance(),a.getType(),a.getCreationDate(),a.getLastDepositDate(),a.isActive(),a.getUser() );
    }

    public List<AccountDTO> findByUserId(Long userId) {
    var accountByUserId = repository.findByUserId(userId);
    return accountByUserId.stream()
            .map(this::convertToAccountDTO)
            .collect(Collectors.toList());

    }
}
