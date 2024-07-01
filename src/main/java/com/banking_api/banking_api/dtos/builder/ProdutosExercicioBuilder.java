package com.banking_api.banking_api.dtos.builder;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class ProdutosExercicioBuilder {
    private Long id ;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;

}
