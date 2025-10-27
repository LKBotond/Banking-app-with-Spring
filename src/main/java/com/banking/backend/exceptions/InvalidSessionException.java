package com.banking.backend.exceptions;

public class InvalidSessionException extends RuntimeException {
    public InvalidSessionException() { super("Invalid session."); }
}
