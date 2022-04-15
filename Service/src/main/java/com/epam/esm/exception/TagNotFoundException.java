package com.epam.esm.exception;

public class TagNotFoundException extends RuntimeException{
    private final int ERROR_CODE = 40401;

    public int getErrorCode() {
        return ERROR_CODE;
    }

}
