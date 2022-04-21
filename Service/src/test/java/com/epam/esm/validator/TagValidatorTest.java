package com.epam.esm.validator;

import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TagValidatorTest {

    private TagValidator validator;

    @BeforeEach
    void setUp() {
        validator = new TagValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Name", "Tag-name", "another-tag-name", "QWERTY", "qa"})
    void isValidTest(String tagName){
        Tag tag = new Tag("tagName");
        assertTrue(validator.isValid(tag));
    }
}
