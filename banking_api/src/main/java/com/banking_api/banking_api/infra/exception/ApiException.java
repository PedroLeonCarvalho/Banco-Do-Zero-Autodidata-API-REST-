package com.banking_api.banking_api.infra.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception {
    private final HttpStatus httpStatus;

    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }


}
