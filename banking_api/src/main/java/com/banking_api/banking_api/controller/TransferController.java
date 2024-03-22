package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.transactions.transfer.Transfer;
import com.banking_api.banking_api.domain.transactions.withdraw.Withdraw;
import com.banking_api.banking_api.dtos.TransferDTO;
import com.banking_api.banking_api.dtos.WithdrawDTO;
import com.banking_api.banking_api.infra.exception.InsufficientBalanceException;
import com.banking_api.banking_api.infra.exception.UnauthorizedUserException;
import com.banking_api.banking_api.service.TransferService;
import com.banking_api.banking_api.service.WithdrawService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {

        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<TransferDTO> toTransfer (@RequestBody TransferDTO dto, Authentication auth) throws EntityNotFoundException, InsufficientBalanceException, UnauthorizedUserException {
        var username = auth.getName();
        var newTransfer = transferService.transfer(dto, username);
        return ResponseEntity.ok(newTransfer);
    }
}