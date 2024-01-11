package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Student;
import com.sleepypoem.testplatform.service.StudentService;
import com.sleepypoem.testplatform.testutils.factories.impl.StudentFactory;
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
class StudentValidatorTest {

    private StudentValidator studentValidator;

    private StudentFactory factory;

    @Mock
    private StudentService studentService;

    @BeforeEach
    void setup() {
        studentValidator = new StudentValidator();
        factory = new StudentFactory();
        studentValidator.setStudentService(studentService);
    }

    @Test
    @DisplayName("Test no error is present if student is valid")
    void testStudentIsValid() {
        Student student = factory.create();
        Map<String, String> errors = studentValidator.isValid(student);
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Test error is present if student's first name is null or empty")
    void testStudentFirstNameIsNullOrEmpty() {
        Student student = factory.create();
        student.setFirstName(null);
        Map<String, String> errors = studentValidator.isValid(student);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("firstName"));
        assertEquals("First name is null or empty", errors.get("firstName"));
    }

    @Test
    @DisplayName("Test error is present if student's last name is null or empty")
    void testStudentLastNameIsNullOrEmpty() {
        Student student = factory.create();
        student.setLastName(null);
        Map<String, String> errors = studentValidator.isValid(student);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("lastName"));
        assertEquals("Last name is null or empty", errors.get("lastName"));
    }

    @Test
    @DisplayName("Test error is present when grade level is less than zero")
    void testGradeLevelIsLessThanZero() {
        Student student = factory.create();
        student.setGradeLevel(-1);
        Map<String, String> errors = studentValidator.isValid(student);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("gradeLevel"));
        assertEquals("Grade level is less than or equal to 0", errors.get("gradeLevel"));
    }

    @Test
    @DisplayName("Test error is present when student code is null or invalid")
    void testStudentCodeIsNullOrInvalid() {
        Student student = factory.create();
        student.setStudentCode(-1L);
        Map<String, String> errors = studentValidator.isValid(student);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("studentCode"));
        assertEquals("Student code is null or invalid", errors.get("studentCode"));
        student.setStudentCode(null);
        errors = studentValidator.isValid(student);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("studentCode"));
        assertEquals("Student code is null or invalid", errors.get("studentCode"));
    }

    @Test
    @DisplayName("Test error is present when student code already exists")
    void testStudentCodeAlreadyExists() {
        Student student = factory.create();
        student.setStudentCode(1L);
        when(studentService.existsByStudentCode(1L)).thenReturn(true).thenReturn(true);
        Map<String, String> errors = studentValidator.isValid(student);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("studentCode"));
        assertEquals("Student code already exists", errors.get("studentCode"));
    }


}