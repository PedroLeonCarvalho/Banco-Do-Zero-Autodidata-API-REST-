package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.dtos.*;
import com.banking_api.banking_api.infra.exception.BadResponseException;
import com.banking_api.banking_api.repository.AccountRepository;
import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import org.json.JSONException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final DepositService depositService;

    private final RestTemplate restTemplate;

    private final UserService userService;

    public AccountService(AccountRepository repository, @Lazy DepositService depositService, RestTemplate restTemplate, UserService userService) {
        this.repository = repository;
        this.depositService = depositService;
        this.restTemplate = restTemplate;
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

    //Chama a cada 1 minuto
    // @Scheduled(cron = "0 * * ? * *")
    @Scheduled(cron = "0 0 0 * * ?")
    public void earningsGenerate() throws JSONException, BadResponseException {
        var accounts = repository.findAccountsActiveAndPoupanca();
        if (accounts == null || accounts.isEmpty()) {
            throw new EntityNotFoundException("Não há contas Poupança ativas com rendimentos pendentes");
        } else {
            for (Account account : accounts) {
                updateBalanceWithEarnings(account);
            }
        }

    }


    public void updateBalanceWithEarnings(Account account) throws JSONException, BadResponseException {
        BigDecimal newBalance = calculateBalancePlusEarnings(account);
        account.setBalance(newBalance);
        repository.save(account);
    }


    private BigDecimal calculateBalancePlusEarnings(Account account) throws JSONException, BadResponseException {

        var value = getSelicDataValue();
        BigDecimal earningsAmount = new BigDecimal("0.005").add(value);
        BigDecimal oldBalance = account.getBalance();
        BigDecimal increase = oldBalance.multiply(earningsAmount);

        return oldBalance.add(increase);
    }


    public BigDecimal getSelicDataValue() throws BadResponseException {
        var dataInicial = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        var dataFinal = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String url = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados?formato=json&dataInicial=" + dataInicial + "&dataFinal=" + dataFinal;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
if(response.getStatusCode().is2xxSuccessful()) {

        var jsonArray = response.getBody();

       var gson = new Gson();
        Type listType = new TypeToken<List<SelicDTO>>() {
        }.getType();

        List<SelicDTO> gsonList = gson.fromJson(jsonArray, listType);

        var valor = gsonList.get(0).getValor();

        return new BigDecimal(valor);

    } else throw new BadResponseException("A taxa SELIC nao pode ser calculado por mal funcionamento do site do banco");
}}










