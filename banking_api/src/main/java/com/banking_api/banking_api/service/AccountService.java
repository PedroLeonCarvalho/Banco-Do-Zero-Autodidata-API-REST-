package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.dtos.*;
import com.banking_api.banking_api.repository.AccountRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final DepositService depositService;

    private final UserService userService;
    public AccountService(AccountRepository repository, @Lazy DepositService depositService, UserService userService) {
        this.repository = repository;
        this.depositService = depositService;
        this.userService = userService;
    }

    public Account createAccount(AccountDTO dto) throws Exception {

        Account account = new Account();
        account.setAccountNumber(dto.accountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setCreationDate(new Date());
        account.setType(dto.type());
        account.setActive(true);
        account.setUser( userService.findUserById(dto.user()));
        account.setLastDepositDate(depositService.getLastDepositDate());

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

    @Transactional
    public Page<AccountListDTO> getAllActiveAccounts(Pageable page) {
        var accounts = repository.findAllByActiveTrue(page);
        return accounts.map(a -> new AccountListDTO(a.getAccountNumber(), a.getType(), a.isActive(), a.getUser().getName()));
    }

    public AccountDTO findById(Long id) throws Exception {
        var account = repository.findById(id).orElseThrow(() -> new Exception("Usuario nao encontrado"));

        return convertToAccountDTO(account);
    }

    public Account findByAccountId(Long id) throws Exception {

        var account = repository.findById(id).orElseThrow(() -> new Exception("Id da conta não enoontrado"));
        return account;
    }

    private AccountDTO convertToAccountDTO(Account a) {
        return new AccountDTO(a.getAccountNumber(), a.getBalance(), a.getType(), a.getCreationDate(), a.getLastDepositDate(), a.isActive(), a.getUser().getId());
    }

    public List<AccountDTO> findByUserId(Long userId) {
        var accountByUserId = repository.findByUserId(userId);
        return accountByUserId.stream()
                .map(this::convertToAccountDTO)
                .collect(Collectors.toList());

    }

    public void save(Account account) {
        repository.save(account);
    }
}
