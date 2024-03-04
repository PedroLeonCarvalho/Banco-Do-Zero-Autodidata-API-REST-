package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.dtos.AccountDTO;
import com.banking_api.banking_api.dtos.AccountDeleteDto;
import com.banking_api.banking_api.dtos.AccountListDTO;
import com.banking_api.banking_api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {


    @Autowired
    private AccountService accountService;

    @PostMapping
    @Transactional
    public ResponseEntity <Account> createAccount(@RequestBody AccountDTO dto){
    var account = accountService.createAccount(dto);
    return new ResponseEntity<>(account, HttpStatus.OK);
    }

@DeleteMapping
@Transactional
public ResponseEntity  deactivate(@RequestBody AccountDeleteDto id ) throws Exception {
    accountService.delete(id);
    return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity <Page<AccountListDTO>> list (@PageableDefault(size = 10) Pageable page) {
        var accounts = accountService.getAllActiveAccounts(page);
        return ResponseEntity.ok(accounts);
    }
    @GetMapping("/{id}")
    public ResponseEntity <AccountDTO> findAccountById ( @PathVariable Long id) throws Exception {
        var account = accountService.findById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/by-user")
    public ResponseEntity<List<AccountDTO>> findAccountByUserId(@RequestParam Long userId) throws Exception {
        var account = accountService.findByUserId(userId);
        return ResponseEntity.ok(account);
    }

}

