package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.infra.exception.BadResponseException;
import com.banking_api.banking_api.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/earnings")
public class EarningsController {

    private final AccountService accountService;


    public EarningsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity generateEarnings() throws BadResponseException {
        accountService.earningsGenerate();
        return ResponseEntity.ok("Rendimentos gerados");
    }


}



