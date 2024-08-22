package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.transactions.transfer.Transfer;
import com.banking_api.banking_api.domain.transactions.withdraw.Withdraw;
import com.banking_api.banking_api.dtos.TransferDTO;
import com.banking_api.banking_api.dtos.WithdrawDTO;
import com.banking_api.banking_api.infra.exception.InsufficientBalanceException;
import com.banking_api.banking_api.infra.exception.UnauthorizedUserException;
import com.banking_api.banking_api.service.TransferService;
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
@RequestMapping("/transfer")
@Tag(name = "Transfer/ Transferência ", description = "Transfer from logged account to other account/ Transfere da conta logada para outra conta")

public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {

        this.transferService = transferService;
    }

    @PostMapping
    @Operation(summary = "Transfer/ Transferir", description = " Fill with the sender and receiver account ID and the value/ Preencha com o ID da conta de quem envia e recebe para tranferir valores")
    public ResponseEntity<TransferDTO> toTransfer (@RequestBody @Valid TransferDTO dto, Authentication auth) throws EntityNotFoundException, InsufficientBalanceException, UnauthorizedUserException {
        var username = auth.getName();
        var newTransfer = transferService.transfer(dto, username);
        return ResponseEntity.ok(newTransfer);
    }
}