package com.banking_api.banking_api.dtos.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public   class WithdrawDTOBuilder {

    private WithdrawDTOExercicioSimulacao withdrawDTOExercicioSimulacao;


    public WithdrawDTOBuilder( ) {
        this.withdrawDTOExercicioSimulacao = new WithdrawDTOExercicioSimulacao();
    }


    public WithdrawDTOBuilder buildId (Long id) {
        this.withdrawDTOExercicioSimulacao.setId(id);
        return this;
    }

    public WithdrawDTOBuilder buildAccount(Long account) {
        this.withdrawDTOExercicioSimulacao.setAccount(account);
        return this;
    }

    public WithdrawDTOBuilder buildValue(BigDecimal value) {
        this.withdrawDTOExercicioSimulacao.setValue(value);
        return this;
    }

    public WithdrawDTOBuilder buildAccountId(Long accountId) {
        this.withdrawDTOExercicioSimulacao.setAccountId(accountId);
        return this;
    }

    public WithdrawDTOBuilder buildTimestamp(LocalDateTime timestamp) {
        this.withdrawDTOExercicioSimulacao.setTimestamp(timestamp);
        return this;
    }

    public WithdrawDTOBuilder buildNewBalance(BigDecimal newBalance) {
        this.withdrawDTOExercicioSimulacao.setNewBalance(newBalance);
        return this;
    }

    public WithdrawDTOExercicioSimulacao build() {
        return this.withdrawDTOExercicioSimulacao;
    }
}
