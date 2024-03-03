package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.dtos.AccountDTO;
import com.banking_api.banking_api.dtos.AccountDeleteDto;
import com.banking_api.banking_api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
}

