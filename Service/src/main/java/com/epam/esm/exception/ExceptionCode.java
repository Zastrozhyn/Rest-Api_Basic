package com.epam.esm.exception;

public enum ExceptionCode {
    TAG_NOT_FOUND(40401),
    GIFT_CERTIFICATE_NOT_FOUND(40301),
    NOT_VALID_TAG_DATA(40403),
    NOT_VALID_GIFT_CERTIFICATE_DATA(40303),
    WRONG_FIND_PARAMETERS(40501);
    private final int errorCode;

    ExceptionCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
