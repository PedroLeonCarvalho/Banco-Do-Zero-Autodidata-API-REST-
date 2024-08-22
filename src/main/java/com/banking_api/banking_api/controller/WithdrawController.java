package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.transactions.deposit.Deposit;
import com.banking_api.banking_api.domain.transactions.withdraw.Withdraw;
import com.banking_api.banking_api.dtos.DepositDTO;
import com.banking_api.banking_api.dtos.WithdrawDTO;
import com.banking_api.banking_api.dtos.WithdrawResponseDTO;
import com.banking_api.banking_api.infra.exception.InsufficientBalanceException;
import com.banking_api.banking_api.infra.exception.UnauthorizedUserException;
import com.banking_api.banking_api.service.DepositService;
import com.banking_api.banking_api.service.WithdrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/withdraw")
@Tag(name = "Withdrawl value/ Saca valor ", description = "Withdrawl value from your account only")

public class WithdrawController {

    private final WithdrawService withdrawService;

    public WithdrawController(WithdrawService withdrawService) {

        this.withdrawService = withdrawService;
    }

    @PostMapping
    @Operation(summary = "to withdrawl/ sacar valor", description = " Must be logged / precisa estar logado")

    public ResponseEntity<WithdrawResponseDTO> toWithdraw (@RequestBody @Valid WithdrawDTO dto,
                                                           Authentication auth) throws EntityNotFoundException, InsufficientBalanceException, UnauthorizedUserException {
    var username = auth.getName();
        var newWithdraw = withdrawService.withdraw(dto, username);
        return ResponseEntity.ok(newWithdraw);
    }
}