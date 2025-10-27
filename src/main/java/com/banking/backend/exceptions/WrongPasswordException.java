package com.banking.backend.exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Incorrect password.");
    }
}