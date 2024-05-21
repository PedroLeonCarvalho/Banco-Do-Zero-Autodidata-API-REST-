package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.AccountType;
import com.banking_api.banking_api.dtos.SelicDTO;
import com.banking_api.banking_api.infra.exception.BadResponseException;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class EarningsRateForNormalAccount implements IEarningsRateStrategy {

    private  RestTemplate restTemplate;

    public EarningsRateForNormalAccount(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EarningsRateForNormalAccount() {
    }

    @Cacheable("taxaSelic")
    @Override
    public BigDecimal getSelicDataValue(AccountType type) throws BadResponseException {
            var dataInicial = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            var dataFinal = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            String url = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.11/dados?formato=json&dataInicial=" + dataInicial + "&dataFinal=" + dataFinal;

            ResponseEntity<SelicDTO[]> response = restTemplate.getForEntity(url, SelicDTO[].class);
            if(response.getStatusCode().is2xxSuccessful()) {

                SelicDTO[] selicDTOArray = response.getBody();

                var retorno = Arrays.stream(selicDTOArray)
                        .map(SelicDTO::getValor)
                        .collect(Collectors.toList());

                return retorno.get(0);

            } else throw new BadResponseException("A taxa SELIC nao pode ser calculado por mal funcionamento do site do banco");
        }
    }

