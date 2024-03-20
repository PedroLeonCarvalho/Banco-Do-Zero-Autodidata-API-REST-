package com.banking_api.banking_api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;



public class InsufficientBalanceException extends ApiException {


        public InsufficientBalanceException(String msg) {
            super(msg , HttpStatus.PAYMENT_REQUIRED);
        }


    }



