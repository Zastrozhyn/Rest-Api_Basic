package com.epam.esm.exception;

public class TagAlreadyExistsException extends RuntimeException{
    private final int ERROR_CODE = 40001;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
