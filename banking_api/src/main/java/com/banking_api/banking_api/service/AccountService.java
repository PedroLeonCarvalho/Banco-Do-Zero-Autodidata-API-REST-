package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.dtos.AccountDTO;
import com.banking_api.banking_api.dtos.AccountDeleteDto;
import com.banking_api.banking_api.dtos.AccountListDTO;
import com.banking_api.banking_api.dtos.SelicDTO;
import com.banking_api.banking_api.infra.exception.BadResponseException;
import com.banking_api.banking_api.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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
@Cacheable("accountById")
    public Account findByAccountId(Long id) throws EntityNotFoundException {
        return  repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id da conta não enoontrado"));

    }

    private AccountDTO convertToAccountDTO(Account a) {
        return new AccountDTO(a.getAccountNumber(), a.getBalance(), a.getType(), a.getCreationDate(), a.getLastDepositDate(), a.isActive(), a.getUser().getId());
    }
    @Cacheable("userById")
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
    public void earningsGenerate()  {
        var accounts = repository.findOptionalAccountsActiveAndPoupanca().orElseThrow(() -> new EntityNotFoundException("Conta não encontrada com o ID"));
        accounts.forEach(account -> {
            try {
                updateBalanceWithEarnings(account);
            } catch (BadResponseException e) {
                throw new RuntimeException ("Erro no site do banco ao restornar a resposta");
            }
        });

        }



    public void updateBalanceWithEarnings(Account account) throws BadResponseException {
        BigDecimal newBalance = calculateBalancePlusEarnings(account);
        account.setBalance(newBalance);
        repository.save(account);
    }


    private BigDecimal calculateBalancePlusEarnings(Account account) throws BadResponseException {

        var value = getSelicDataValue();
        BigDecimal earningsAmount = new BigDecimal("0.005").add(value);
        BigDecimal oldBalance = account.getBalance();
        BigDecimal increase = oldBalance.multiply(earningsAmount);

        return oldBalance.add(increase);
    }

@Cacheable("Selic")
    public BigDecimal getSelicDataValue() throws BadResponseException {
        var dataInicial = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        var dataFinal = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String url = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados?formato=json&dataInicial=" + dataInicial + "&dataFinal=" + dataFinal;

        ResponseEntity<SelicDTO[]> response = restTemplate.getForEntity(url, SelicDTO[].class);
        if(response.getStatusCode().is2xxSuccessful()) {

            SelicDTO[] selicDTOArray = response.getBody();

            var retorno = Arrays.stream(selicDTOArray)
                    .map(SelicDTO::getValor)
                    .collect(Collectors.toList());


            return new BigDecimal(retorno.get(0));

        } else throw new BadResponseException("A taxa SELIC nao pode ser calculado por mal funcionamento do site do banco");
    }

}










