package com.banking_api.banking_api.dtos;

import com.banking_api.banking_api.domain.account.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepositDTO {
       private Long accountId;
        private BigDecimal value;
        private BigDecimal newBalance;


}
