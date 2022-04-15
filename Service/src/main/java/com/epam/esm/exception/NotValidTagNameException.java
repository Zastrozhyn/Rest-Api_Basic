package com.epam.esm.exception;

public class NotValidTagNameException extends RuntimeException{
    private final int ERROR_CODE = 40002;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
