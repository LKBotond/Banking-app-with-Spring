package com.banking.backend.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("No such account in DataBase");
    }
}
