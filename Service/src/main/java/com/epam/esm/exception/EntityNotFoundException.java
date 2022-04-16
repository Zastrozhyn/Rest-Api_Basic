package com.epam.esm.exception;

public class EntityNotFoundException extends RuntimeException{
    private final int ERROR_CODE = 40401;

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }

}
