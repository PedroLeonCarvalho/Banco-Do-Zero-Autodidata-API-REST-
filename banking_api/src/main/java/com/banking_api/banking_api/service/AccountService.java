package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.account.Earnings;
import com.banking_api.banking_api.dtos.*;
import com.banking_api.banking_api.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final DepositService depositService;

    private final UserService userService;

    public AccountService(AccountRepository repository, @Lazy DepositService depositService, UserService userService) {
        this.repository = repository;
        this.depositService = depositService;
        this.userService = userService;
    }

    public Account createAccount(AccountDTO dto) throws EntityNotFoundException {

        Account account = new Account();
        account.setAccountNumber(dto.accountNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setCreationDate(LocalDate.now());
        account.setType(dto.type());
        account.setActive(true);
        account.setUser(userService.findUserById(dto.user()));
        account.setLastDepositDate(depositService.getLastDepositDate());

        repository.save(account);
        return account;
    }


    public void delete(AccountDeleteDto id) throws EntityNotFoundException {
        if (!repository.existsById(id.id())) {
            throw new EntityNotFoundException("Conta não existe");
        } else {
            repository.deactivateAccountById(id.id());
        }
    }


    public Page<AccountListDTO> getAllActiveAccounts(Pageable page) {
        var accounts = repository.findAllByActiveTrue(page);
        return accounts.map(a -> new AccountListDTO(a.getAccountNumber(), a.getType(), a.isActive(), a.getUser().getName()));
    }

    public AccountDTO findById(Long id) throws EntityNotFoundException {
        var account = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));

        return convertToAccountDTO(account);
    }

    public Account findByAccountId(Long id) throws EntityNotFoundException {

        var account = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id da conta não enoontrado"));
        return account;
    }

    private AccountDTO convertToAccountDTO(Account a) {
        return new AccountDTO(a.getAccountNumber(), a.getBalance(), a.getType(), a.getCreationDate(), a.getLastDepositDate(), a.isActive(), a.getUser().getId());
    }

    public List<AccountDTO> findByUserId(Long userId) {
        var accountByUserId = repository.findByUserId(userId);
        return accountByUserId.stream()
                .map(this::convertToAccountDTO)
                .collect(Collectors.toList());

    }

    public void save(Account account) {
        repository.save(account);
    }


    public void earningsGenerate() {
       var accounts = repository.findAccountsActiveAndPoupanca ();

        accounts.forEach(this::updateBalanceWithEarnings);

    }
//Método extra pra usar o "reference method"
    private void updateBalanceWithEarnings(Account account) {
        BigDecimal newBalance = calculateBalancePlusEarnings(account);
        account.setBalance(newBalance);
    }
//   código antigo
//    private BigDecimal calculateBalancePlusEarnings() {
//        var earnings = new Earnings();
//        var account = new Account();
//         earnings.setEarningsAmount(BigDecimal.valueOf(0.01));
//         var oldBalance = account.getBalance();
//        var increase= oldBalance.multiply(earnings.getEarningsAmount());
//
//        return oldBalance.add(increase);

        private BigDecimal calculateBalancePlusEarnings(Account account) {
            BigDecimal earningsAmount = new BigDecimal("0.01"); // Defina o valor dos ganhos aqui
            BigDecimal oldBalance = account.getBalance();
            BigDecimal increase = oldBalance.multiply(earningsAmount);
            return oldBalance.add(increase);
        }


    }







