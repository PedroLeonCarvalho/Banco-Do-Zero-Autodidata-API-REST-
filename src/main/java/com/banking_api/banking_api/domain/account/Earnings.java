package com.banking_api.banking_api.domain.account;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Embeddable
@Getter
@Setter
@AttributeOverrides({
        @AttributeOverride(name = "earningsAmount", column = @Column(name = "earnings_amount")),
        @AttributeOverride(name = "earningsDate", column = @Column(name = "earnings_date"))
})
public class Earnings {

 private BigDecimal earningsAmount;
 private LocalDate earningsDate;
}
