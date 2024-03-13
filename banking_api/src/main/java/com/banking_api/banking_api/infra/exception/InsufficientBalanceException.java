package com.banking_api.banking_api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


public class InsufficientBalanceException extends RuntimeException {


        private static final HttpStatus HTTP_STATUS = HttpStatus.PAYMENT_REQUIRED;

        public InsufficientBalanceException(String msg) {
            super(msg);
        }
            public HttpStatus getHttpStatus() {
                return HTTP_STATUS;

        }
    }



