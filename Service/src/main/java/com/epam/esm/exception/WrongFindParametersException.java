package com.epam.esm.exception;

public class WrongFindParametersException extends RuntimeException {
    private final int ERROR_CODE = 40501;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
