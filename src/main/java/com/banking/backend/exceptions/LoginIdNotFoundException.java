package com.banking.backend.exceptions;

public class LoginIdNotFoundException extends RuntimeException {
    public LoginIdNotFoundException() {
        super("Login id not found");
    }
}
