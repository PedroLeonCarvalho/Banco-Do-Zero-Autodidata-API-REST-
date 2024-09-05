package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.infra.exception.BadResponseException;
import com.banking_api.banking_api.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/earnings")
@Tag(name = "Genarate Account Earnings / Gera rendimentos", description = "Account will be add by a current earning acording to its type: PREMIUM or SAVING")

public class EarningsController {

    private final AccountService accountService;


    public EarningsController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    @CacheEvict(value= "taxaSelic")
    @Scheduled(cron = "0 0 0 * * ?")
    @Operation(summary = "Generate Earnings/ Gera rendimentos", description = "This endpoint integrates with real external banking API for getting the  current earnings values and is scheduled to be called every 24 hours / Integrado com API de banco real para pegar os valores de juros atuais e é chamada a cada 24 horas")
    public ResponseEntity generateEarnings() throws BadResponseException {
        accountService.earningsGenerate();
        return ResponseEntity.ok("Rendimentos gerados");
    }


}



