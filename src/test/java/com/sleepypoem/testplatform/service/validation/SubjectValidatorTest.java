package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Subject;
import com.sleepypoem.testplatform.testutils.factories.impl.SubjectFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SubjectValidatorTest {

    private SubjectValidator validator;

    private SubjectFactory factory;

    @BeforeEach
    void setUp() {
        validator = new SubjectValidator();
        factory = new SubjectFactory();
    }

    @Test
    @DisplayName("Test no error is present when subject is valid")
    void testValidSubject() {
        Subject subject = factory.create();
        Map<String, String> errors = validator.isValid(subject);
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Test error is present when subject name is null or empty")
    void testInvalidSubject() {
        Subject subject = factory.create();
        subject.setName(null);
        Map<String, String> errors = validator.isValid(subject);
        assertFalse(errors.isEmpty());
        assertEquals("name is null or empty", errors.get("name"));

        subject.setName("");
        errors = validator.isValid(subject);
        assertFalse(errors.isEmpty());
        assertEquals("name is null or empty", errors.get("name"));
    }

}