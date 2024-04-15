package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.dtos.AccountDTO;
import com.banking_api.banking_api.dtos.AccountDeleteDto;
import com.banking_api.banking_api.dtos.AccountListDTO;
import com.banking_api.banking_api.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;

    public AccountController(AccountService accountService, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    @CachePut(value="accountsList")
    public ResponseEntity<Account> createAccount(@RequestBody @Valid AccountDTO dto) throws EntityNotFoundException {
        var account = accountService.createAccount(dto);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @DeleteMapping
    @CacheEvict(value="accountsList")
    public ResponseEntity deactivate(@RequestBody @Valid AccountDeleteDto id) throws EntityNotFoundException {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Cacheable("accountsList")
    public ResponseEntity<Page<AccountListDTO>> list(@PageableDefault(size = 10) Pageable page) {
        var accounts = accountService.getAllActiveAccounts(page);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findAccountById(@PathVariable Long id) throws EntityNotFoundException {
        var account = accountService.findById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<AccountDTO>> findAccountByUserId(@RequestParam Long userId) throws EntityNotFoundException {
        var account = accountService.findByUserId(userId);
        return ResponseEntity.ok(account);
    }

}

