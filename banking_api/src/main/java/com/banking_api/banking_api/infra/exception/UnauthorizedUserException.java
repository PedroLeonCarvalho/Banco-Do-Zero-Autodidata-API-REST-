package com.banking_api.banking_api.infra.exception;

import org.springframework.http.HttpStatus;


public class UnauthorizedUserException extends ApiException {


        public UnauthorizedUserException(String msg) {

            super(msg , HttpStatus.UNAUTHORIZED);
        }




    }



