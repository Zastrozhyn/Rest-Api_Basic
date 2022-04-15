package com.epam.esm.exception;

public class NotValidCertificateDataException extends RuntimeException{
    private final int ERROR_CODE = 40003;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
