package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.transactions.deposit.Deposit;
import com.banking_api.banking_api.domain.transactions.withdraw.Withdraw;
import com.banking_api.banking_api.dtos.DepositDTO;
import com.banking_api.banking_api.dtos.WithdrawDTO;
import com.banking_api.banking_api.infra.exception.InsufficientBalanceException;
import com.banking_api.banking_api.service.DepositService;
import com.banking_api.banking_api.service.WithdrawService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/withdraw")
public class WithdrawController {

    private final WithdrawService withdrawService;

    public WithdrawController(WithdrawService withdrawService) {

        this.withdrawService = withdrawService;
    }

    @PostMapping
    public ResponseEntity<WithdrawDTO> toWithdraw (@RequestBody WithdrawDTO dto) throws EntityNotFoundException , InsufficientBalanceException{
        var newWithdraw = withdrawService.withdraw(dto);
        return ResponseEntity.ok(newWithdraw);
    }
}