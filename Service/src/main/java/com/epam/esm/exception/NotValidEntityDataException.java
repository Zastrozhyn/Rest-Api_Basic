package com.epam.esm.exception;

public class NotValidEntityDataException extends RuntimeException{
    private final int ERROR_CODE = 40403;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
