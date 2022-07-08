package com.epam.esm.validator;

import com.epam.esm.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {
    private static UserValidator validator;

    @BeforeAll
    static void setUp() {
        validator = new UserValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Name", "User-name", "another-user-name", "QWERTY", "qa"})
    void isValidTest(String name){
        User user = new User();
        user.setName(name);
        assertTrue(validator.isValid(user));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "b", "1"})
    void isValidTestFalse(String name){
        User user = new User();
        user.setName(name);
        assertFalse(validator.isValid(user));
    }
}
