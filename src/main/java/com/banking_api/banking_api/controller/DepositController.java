package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.transactions.deposit.Deposit;
import com.banking_api.banking_api.dtos.DepositDTO;
import com.banking_api.banking_api.service.DepositService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/deposit")
@Tag(name = "Deposit / Depósito", description = " Make deposits for any account registered / Faça depósitos para qualquer conta registrada")

public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping
    @Operation(summary = "Deposit now/ Depositar", description = "Insert value and account ID destination / Insira o valor e o ID da conta de destino")

    public ResponseEntity<DepositDTO> toDeposit(@RequestBody @Valid DepositDTO dto) throws EntityNotFoundException {
        var newDeposit = depositService.deposit(dto);
        return ResponseEntity.ok(newDeposit);
    }
}