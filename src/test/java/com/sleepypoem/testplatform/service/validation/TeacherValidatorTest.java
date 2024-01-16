package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.service.TeacherService;
import com.sleepypoem.testplatform.testutils.factories.impl.TeacherFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherValidatorTest {

    private TeacherValidator validator;

    private TeacherFactory factory;

    @Mock
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        validator = new TeacherValidator();
        factory = new TeacherFactory();
        validator.setTeacherService(teacherService);
    }

    @Test
    @DisplayName("Test no error is present when teacher is valid")
    void testValidTeacher() {
        Teacher teacher = factory.create();
        teacher.setId(null);
        when(teacherService.existsByTeacherCode(anyLong())).thenReturn(false);
        Map<String, String> errors = validator.isValid(teacher);
        assertTrue(errors.isEmpty());
        verify(teacherService).existsByTeacherCode(teacher.getTeacherCode());
    }

    @Test
    @DisplayName("Test error is present when teacher first name is null or  empty")
    void testInvalidTeacherName() {
        Teacher teacher = factory.create();
        teacher.setId(null);
        when(teacherService.existsByTeacherCode(anyLong())).thenReturn(false).thenReturn(false);
        teacher.setFirstName(null);
        Map<String, String> errors = validator.isValid(teacher);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("firstName"));
        assertEquals("First name is null or empty", errors.get("firstName"));
        teacher.setFirstName("");
        errors = validator.isValid(teacher);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("firstName"));
        assertEquals("First name is null or empty", errors.get("firstName"));
        verify(teacherService, times(2)).existsByTeacherCode(teacher.getTeacherCode());
    }

    @Test
    @DisplayName("Test error is present when teacher last name is null or empty")
    void testInvalidTeacherLastName() {
        Teacher teacher = factory.create();
        teacher.setId(null);
        when(teacherService.existsByTeacherCode(anyLong())).thenReturn(false).thenReturn(false);
        teacher.setLastName("");
        Map<String, String> errors = validator.isValid(teacher);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("lastName"));
        assertEquals("Last name is null or empty", errors.get("lastName"));
        teacher.setLastName(null);
        errors = validator.isValid(teacher);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("lastName"));
        assertEquals("Last name is null or empty", errors.get("lastName"));
        verify(teacherService, times(2)).existsByTeacherCode(teacher.getTeacherCode());
    }

    @Test
    @DisplayName("Test error is present when teacher code is null or less than zero")
    void testInvalidTeacherCodeNullOrLessThanZero() {
        Teacher teacher = factory.create();
        teacher.setId(null);
        when(teacherService.existsByTeacherCode(anyLong())).thenReturn(false);
        teacher.setTeacherCode(null);
        Map<String, String> errors = validator.isValid(teacher);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("teacherCode"));
        assertEquals("Teacher code is null or invalid", errors.get("teacherCode"));
        teacher.setTeacherCode(-1L);
        errors = validator.isValid(teacher);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("teacherCode"));
        assertEquals("Teacher code is null or invalid", errors.get("teacherCode"));
        verify(teacherService).existsByTeacherCode(teacher.getTeacherCode());
    }

    @Test
    @DisplayName("Test error is present when teacher code is already in use")
    void testInvalidTeacherCode() {
        Teacher teacher = factory.create();
        teacher.setId(null);
        when(teacherService.existsByTeacherCode(anyLong())).thenReturn(true);
        Map<String, String> errors = validator.isValid(teacher);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("teacherCode"));
        assertEquals("Teacher code already exists", errors.get("teacherCode"));
        verify(teacherService).existsByTeacherCode(teacher.getTeacherCode());
    }

}