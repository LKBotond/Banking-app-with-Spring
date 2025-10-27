package com.banking.backend.exceptions;

public class RegistrationFailedException extends RuntimeException {
    public RegistrationFailedException() {
        super("Registration failed");
    }
}
