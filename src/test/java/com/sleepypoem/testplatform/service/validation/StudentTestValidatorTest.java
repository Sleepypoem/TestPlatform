package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.StudentTest;
import com.sleepypoem.testplatform.service.StudentService;
import com.sleepypoem.testplatform.service.TestService;
import com.sleepypoem.testplatform.testutils.factories.impl.StudentTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentTestValidatorTest {

    @Mock
    private StudentService studentService;

    @Mock
    private TestService testService;

    private StudentTestValidator validator;

    private StudentTestFactory factory;

    @BeforeEach
    void setUp() {
        validator = new StudentTestValidator(studentService, testService);
        factory = new StudentTestFactory();
    }

    @Test
    @DisplayName("Test no error is present if element is valid")
    void testValidate_noError() {
        StudentTest studentTest = factory.create();
        when(studentService.existsById(anyLong())).thenReturn(true);
        when(testService.existsById(anyLong())).thenReturn(true);
        Map<String, String> errors = validator.isValid(studentTest);
        assertTrue(errors.isEmpty());
        verify(studentService).existsById(anyLong());
        verify(testService).existsById(anyLong());
    }

    @Test
    @DisplayName("Test error is present if the student in the student test is null or does not exists")
    void testValidate_studentError() {
        StudentTest studentTest = factory.create();
        when(studentService.existsById(anyLong())).thenReturn(false);
        Map<String, String> errors = validator.isValid(studentTest);
        assertTrue(errors.containsKey("student"));
        assertEquals("student is null or does not exist", errors.get("student"));
        verify(studentService).existsById(anyLong());
    }

    @Test
    @DisplayName("Test error is present if the test in the student test is null or does not exists")
    void testValidate_testError() {
        StudentTest studentTest = factory.create();
        when(studentService.existsById(anyLong())).thenReturn(true);
        when(testService.existsById(anyLong())).thenReturn(false);
        Map<String, String> errors = validator.isValid(studentTest);
        assertTrue(errors.containsKey("test"));
        assertEquals("test is null or does not exist", errors.get("test"));
        verify(testService).existsById(anyLong());
    }

    @Test
    @DisplayName("Test error is present if the answers are null")
    void testValidate_answersError() {
        StudentTest studentTest = factory.create();
        studentTest.setAnswers(null);
        when(studentService.existsById(anyLong())).thenReturn(true);
        when(testService.existsById(anyLong())).thenReturn(true);
        Map<String, String> errors = validator.isValid(studentTest);
        assertTrue(errors.containsKey("answers"));
        assertEquals("answers is null", errors.get("answers"));
    }
}