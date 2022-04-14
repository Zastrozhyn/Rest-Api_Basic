package com.epam.esm.exception;

public class EntityAlreadyExistsException extends RuntimeException{
    private final int ERROR_CODE = 40001;

    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
