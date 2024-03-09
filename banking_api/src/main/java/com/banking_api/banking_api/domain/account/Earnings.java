package com.banking_api.banking_api.domain.account;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Embeddable
@Getter
@Setter
public class Earnings {

 private BigDecimal earningsAmount;
 private LocalDate earningsDate;
}
