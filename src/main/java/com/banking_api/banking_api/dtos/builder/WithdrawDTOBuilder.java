package com.banking_api.banking_api.dtos.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public   class WithdrawDTOBuilder {

    private WithdrawDTOExercicioSimulacao withdrawDTOExercicioSimulacao;


    public WithdrawDTOBuilder( ) {
        this.withdrawDTOExercicioSimulacao = new WithdrawDTOExercicioSimulacao();
    }


    public WithdrawDTOBuilder id (Long id) {
        this.withdrawDTOExercicioSimulacao.setId(id);
        return this;
    }

    public WithdrawDTOBuilder account(Long account) {
        this.withdrawDTOExercicioSimulacao.setAccount(account);
        return this;
    }

    public WithdrawDTOBuilder value(BigDecimal value) {
        this.withdrawDTOExercicioSimulacao.setValue(value);
        return this;
    }

    public WithdrawDTOBuilder accountId(Long accountId) {
        this.withdrawDTOExercicioSimulacao.setAccountId(accountId);
        return this;
    }

    public WithdrawDTOBuilder timestamp(LocalDateTime timestamp) {
        this.withdrawDTOExercicioSimulacao.setTimestamp(timestamp);
        return this;
    }

    public WithdrawDTOBuilder newBalance(BigDecimal newBalance) {
        this.withdrawDTOExercicioSimulacao.setNewBalance(newBalance);
        return this;
    }

    public WithdrawDTOExercicioSimulacao build() {
        return this.withdrawDTOExercicioSimulacao;
    }
}
