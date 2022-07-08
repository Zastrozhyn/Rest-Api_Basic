package com.epam.esm.validator;

import com.epam.esm.exception.EntityException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateValidatorTest {
    private static GiftCertificateValidator validator;

    @BeforeAll
    static void setUp(){
        validator = new GiftCertificateValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Name",  "another-name", "QWERTY", "qa"})
    void isNameValidTest(String name) {
        assertTrue(validator.isNameValid(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sport",  "anything", "QWERTY", "qa"})
    void isDescriptionValidTest(String description) {
        assertTrue(validator.isDescriptionValid(description));
    }

    @ParameterizedTest
    @ValueSource(doubles = {100,  1000, 1.1, 5})
    void isPriceValidTest(double price) {
        assertTrue(validator.isPriceValid(BigDecimal.valueOf(price)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-100,  1000000, 0})
    void isPriceNotValidTest(double price) {
        assertThrows(EntityException.class, () -> validator.isPriceValid(BigDecimal.valueOf(price)));
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 1, 5})
    void isDurationValid(Integer duration) {
        assertTrue(validator.isDurationValid(duration));
    }

    @Test
    void isDescriptionValidReturnsFalseWithUpdatedMoreThen301SymbolsDescription() {
        String MoreThen301Description = "D".repeat(301);
        assertThrows(EntityException.class, () -> validator.isDescriptionValid(MoreThen301Description));
    }

    @ParameterizedTest
    @ValueSource(strings = {"asc", "desc", "ASC", "DESC", "desC", "AsC"})
    void isOrderSortValidReturnsTrue(String orderSort) {
        boolean condition = validator.isOrderSortValid(orderSort);
        assertTrue(condition);
    }
    @ParameterizedTest
    @ValueSource(strings = {"name", "duration", "price", "create_date"})
    void isGiftCertificateFieldListValidReturnsTrueWithValidParams(String field) {
        boolean condition = validator.isGiftCertificateFieldValid(field);
        assertTrue(condition);
    }

}
