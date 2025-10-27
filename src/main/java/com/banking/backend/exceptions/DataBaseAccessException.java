package com.banking.backend.exceptions;

public class DataBaseAccessException extends RuntimeException {
    public DataBaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }

}
