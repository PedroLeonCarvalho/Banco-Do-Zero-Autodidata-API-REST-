package com.banking_api.banking_api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

 @ExceptionHandler(EntityNotFoundException.class)
 public ResponseEntity<?> notFound404() {
        return ResponseEntity.notFound().build();
    }


@ExceptionHandler (InsufficientBalanceException.class)
    public ResponseEntity<?> paymentRequired402 (ApiException exception) {

      var httpStatus = exception.getStatus();

     return ResponseEntity.status(httpStatus).body(exception.getMessage());
}
    @ExceptionHandler (UnauthorizedUserException.class)
    public ResponseEntity<?> UnaouthorizedUser401 (ApiException exception) {

        var httpStatus = exception.getStatus();

        return ResponseEntity.status(httpStatus).body(exception.getMessage());
    }

}
