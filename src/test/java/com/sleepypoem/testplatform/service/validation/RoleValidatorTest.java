package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Role;
import com.sleepypoem.testplatform.testutils.factories.impl.RoleFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RoleValidatorTest {

    private RoleValidator validator;

    private RoleFactory factory;

    @BeforeEach
    void setUp() {
        validator = new RoleValidator();
        factory = new RoleFactory();
    }

    @Test
    @DisplayName("Test no error is present when role is valid")
    void testValidRole() {
        Role role = factory.create();
        Map<String, String> errors = validator.isValid(role);
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Test error is present when role name is null or empty")
    void testInvalidRoleName() {
        Role role = factory.create();
        role.setName(null);
        Map<String, String> errors = validator.isValid(role);
        assertFalse(errors.isEmpty());
        assertEquals("name is null or empty", errors.get("name"));
        role.setName("");
        errors = validator.isValid(role);
        assertFalse(errors.isEmpty());
        assertEquals("name is null or empty", errors.get("name"));
    }

}