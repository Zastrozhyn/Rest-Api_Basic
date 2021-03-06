package com.epam.esm.exception;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ExceptionControllerAdviser {
    private static final List<String> AVAILABLE_LOCALES = List.of("en_US", "ru_RU");
    private static final Locale DEFAULT_LOCALE = new Locale("en", "US");
    private final ResourceBundleMessageSource bundleMessageSource;

    @Autowired
    public ExceptionControllerAdviser(ResourceBundleMessageSource messages) {
        this.bundleMessageSource = messages;
    }

    @ExceptionHandler(EntityException.class)
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundException(EntityException e, Locale locale) {
        return buildErrorResponse(resolveResourceBundle(getMessageByCode(e.getErrorCode()), locale), e.getErrorCode()
        );
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, NumberFormatException.class})
    public ResponseEntity<ExceptionResponse> handleMessageNotReadableException(Locale locale) {
        int errorCode = ExceptionCode.ERROR_INPUT_DATA.getErrorCode();
        return buildErrorResponse(resolveResourceBundle(getMessageByCode(errorCode), locale), errorCode
        );
    }

    @ExceptionHandler({PSQLException.class, ConnectException.class})
    public ResponseEntity<ExceptionResponse> handleDataBaseException(Locale locale) {
        int errorCode = ExceptionCode.CONNECTION_DATABASE_ERROR.getErrorCode();
        return buildErrorResponse(resolveResourceBundle(getMessageByCode(errorCode), locale), errorCode
        );
    }

    private String resolveResourceBundle(String key, Locale locale) {
        if (!AVAILABLE_LOCALES.contains(locale.toString())) {
            locale = DEFAULT_LOCALE;
        }
        return bundleMessageSource.getMessage(key, null, locale);
    }

    private ResponseEntity<ExceptionResponse> buildErrorResponse(String message, Integer code) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(message, code);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    private String getMessageByCode(int errorCode) {
        return "error_msg." + errorCode;
    }
}