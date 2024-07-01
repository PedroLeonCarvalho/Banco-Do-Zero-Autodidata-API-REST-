package com.banking_api.banking_api.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class SelicDTO {

    private String data;

    private BigDecimal valor;
}
