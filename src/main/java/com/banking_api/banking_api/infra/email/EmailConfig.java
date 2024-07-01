package com.banking_api.banking_api.infra.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EmailConfig {

    @Bean
    public SimpleMailMessage templateDepositSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "Você recebeu um depósito de %s em %s");
        return message;
    }
}
