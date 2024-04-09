package com.banking_api.banking_api.infra.exception;

import org.springframework.http.HttpStatus;


public class BadResponseException extends ApiException {

        public BadResponseException(String msg) {

            super(msg , HttpStatus.OK);
        }




    }



