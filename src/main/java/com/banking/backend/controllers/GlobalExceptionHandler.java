package com.banking.backend.controllers;

import com.banking.backend.exceptions.AccountNotFoundException;
import com.banking.backend.exceptions.DataBaseAccessException;
import com.banking.backend.exceptions.InvalidSessionException;
import com.banking.backend.exceptions.LoginIdNotFoundException;
import com.banking.backend.exceptions.RegistrationFailedException;
import com.banking.backend.exceptions.UserAlreadyExistsException;
import com.banking.backend.exceptions.UserNotFoundException;
import com.banking.backend.exceptions.WrongPasswordException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Void> handleAccountNotFound(AccountNotFoundException ex) {
        log.info("account not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(LoginIdNotFoundException.class)
    public ResponseEntity<Void> handleLoginIdNotFoundException(LoginIdNotFoundException ex) {
        log.warn("Login id not found: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Void> handleUserNotFoundException(UserNotFoundException ex) {
        log.warn("User not found: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(DataBaseAccessException.class)
    public ResponseEntity<Void> handleDatabaseAccess(DataBaseAccessException ex) {
        log.error("Database access error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<Void> handleRegistrationFailedException(RegistrationFailedException ex) {
        log.error("Registration failed: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(InvalidSessionException.class)
    public ResponseEntity<Void> handleInvalidSessionException(InvalidSessionException ex) {
        log.error("Invalid session: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Void> handleWrongPasswordException(WrongPasswordException ex) {
        log.error("Wrong password: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Void> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        log.error("User already exists: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleGenericException(Exception ex) {
        log.error("Unhandled exception:", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
