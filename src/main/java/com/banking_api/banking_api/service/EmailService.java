package com.banking_api.banking_api.service;
import com.banking_api.banking_api.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class EmailService {

    @Autowired
    private  JavaMailSender emailSender;
    @Autowired
    private  SimpleMailMessage template;


    private String writeEmailTemplate(BigDecimal value, LocalDateTime depositTimeStamp) {
        return String.format(template.getText(), value, depositTimeStamp);

    }


    public String getCurrentUserEmail() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            return currentUser.getEmail();
        }
        return null;
    }

    public void sendEmail(BigDecimal value, LocalDateTime depositTimeStamp) {
        String emailBody = writeEmailTemplate(value, depositTimeStamp);
        String subject = " Depósito realizado";
        sendSimpleMessage(getCurrentUserEmail(), subject, emailBody);
    }


private void sendSimpleMessage (String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("noreply@bankingapi.com");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    emailSender.send(message);
}

}


