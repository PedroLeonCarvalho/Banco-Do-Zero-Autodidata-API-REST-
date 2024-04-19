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
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTO dto) throws EntityNotFoundException {
        var account = accountService.createAccount(dto);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deactivate(@PathVariable @Valid Long id) throws EntityNotFoundException {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<Page<AccountListDTO>> list(@PageableDefault(size = 10) Pageable page) {
        var accounts = accountService.cacheList(page); // Chama o método cacheList() explicitamente para armazenar o resultado em cache
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> findAccountById(@PathVariable Long id) throws EntityNotFoundException {
        var account = accountService.findById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<AccountDTO>> findAccountByUserId(@PathVariable Long userId) throws EntityNotFoundException {
        var account = accountService.findByUserId(userId);
        return ResponseEntity.ok(account);
    }

}

