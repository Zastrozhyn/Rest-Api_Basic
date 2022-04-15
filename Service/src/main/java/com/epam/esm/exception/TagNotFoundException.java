package com.epam.esm.exception;

public class TagNotFoundException extends RuntimeException{
    private final int ERROR_CODE = 40401;

    public TagNotFoundException() {
    }

    public TagNotFoundException(String message) {
        super(message);
    }

    public TagNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }

}
