package com.epam.esm.exception;

public class CertificateNotFoundException extends RuntimeException {
    private final int ERROR_CODE = 40004;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
