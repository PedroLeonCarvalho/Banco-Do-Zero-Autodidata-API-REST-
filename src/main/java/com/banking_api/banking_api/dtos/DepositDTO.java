package com.banking_api.banking_api.dtos;

import com.banking_api.banking_api.domain.account.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@EqualsAndHashCode
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepositDTO {

       private Long accountId;

       @Positive(message = "O valor deste campo não pode ser zero ou negativo")
        private BigDecimal value;
        private BigDecimal newBalance;


}
