package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.dtos.AccountDTO;
import com.banking_api.banking_api.dtos.AccountDeleteDto;
import com.banking_api.banking_api.dtos.AccountListDTO;
import com.banking_api.banking_api.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/accounts")
@Tag(name = "Contas Bancárias/ Banking Accounts", description = "Crie, desative e encontre contas / Create, deactivate and find accounts")

public class AccountController {


    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;

    public AccountController(AccountService accountService, AuthenticationManager authenticationManager) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    @Operation(summary = "Criar nova conta/ Create new account", description = "Insira os dados para criar nova conta/ Insert data to create new account")

    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTO dto) throws EntityNotFoundException {
        var account = accountService.createAccount(dto);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar conta/ Dactivate account", description = "Necessario estar logado/  You need to be logged in")

    public ResponseEntity deactivate(@PathVariable @Valid Long id) throws EntityNotFoundException {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    @Operation(summary = "Listas  contas ativas/ list active accounts", description = "Necessario estar logado/  You need to be logged in / How to call this: {\n" +
            "  \"page\": 0,\n" +
            "  \"size\": 10,\n" +
            "  \"sort\": [\n" +
            "    \n" + "  ]\n" + "}")
    public ResponseEntity<Page<AccountListDTO>> list(@PageableDefault(size = 10) Pageable page) {
        var accounts = accountService.cacheList(page); // Chama o método cacheList() explicitamente para armazenar o resultado em cache
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Econtra conta pelo ID da conta/ Find account by account ID", description = "Necessario estar logado/  You need to be logged in")
    public ResponseEntity<AccountDTO> findAccountById(@PathVariable Long id) throws EntityNotFoundException {
        var account = accountService.findById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/by-user/{userId}")
    @Operation(summary = "Econtra conta pelo ID da usuaŕio/ Find account by user ID", description = "Necessario estar logado/  You need to be logged in")

    public ResponseEntity<List<AccountDTO>> findAccountByUserId(@PathVariable Long userId) throws EntityNotFoundException {
        var account = accountService.findByUserId(userId);
        return ResponseEntity.ok(account);
    }

}

