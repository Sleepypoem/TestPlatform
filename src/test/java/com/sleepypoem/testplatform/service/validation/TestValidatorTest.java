package com.sleepypoem.testplatform.service.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sleepypoem.testplatform.domain.dto.Question;
import com.sleepypoem.testplatform.domain.utils.QuestionParser;
import com.sleepypoem.testplatform.service.SubjectService;
import com.sleepypoem.testplatform.service.TeacherService;
import com.sleepypoem.testplatform.testutils.factories.impl.TestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestValidatorTest {

    @Mock
    TeacherService teacherService;

    @Mock
    SubjectService subjectService;

    TestFactory testFactory;

    TestValidator testValidator;

    QuestionParser questionParser;

    ObjectMapper objectMapper;

    @Mock
    QuestionValidator questionValidator;

    @BeforeEach
    void setUp() {
        testFactory = new TestFactory();
        objectMapper = new ObjectMapper();
        questionParser = new QuestionParser(objectMapper);
        testValidator = new TestValidator(teacherService, subjectService, questionParser, questionValidator);
    }

    @Test
    @DisplayName("Test error is present when teacher is null")
    void testErrorIsPresentWhenTeacherIsNull() {
        com.sleepypoem.testplatform.domain.entity.Test test = testFactory.create();
        test.setTeacher(null);
        Map<String,String> errors = testValidator.isValid(test);
        assertTrue(errors.containsKey("teacher"));
        assertEquals("teacher is null or does not exist", errors.get("teacher"));
    }

    @Test
    @DisplayName("Test error is present when teacher does not exists")
    void testErrorIsPresentWhenTeacherDoesNotExist() {
        com.sleepypoem.testplatform.domain.entity.Test test = testFactory.create();
        when(teacherService.existsById(test.getTeacher().getId())).thenReturn(false);
        Map<String,String> errors = testValidator.isValid(test);
        assertTrue(errors.containsKey("teacher"));
        assertEquals("teacher is null or does not exist", errors.get("teacher"));
    }

    @Test
    @DisplayName("Test error is present when subject is null")
    void testErrorIsPresentWhenSubjectIsNull() {
        com.sleepypoem.testplatform.domain.entity.Test test = testFactory.create();
        test.setSubject(null);
        Map<String,String> errors = testValidator.isValid(test);
        assertTrue(errors.containsKey("subject"));
        assertEquals("subject is null or does not exist", errors.get("subject"));
    }

    @Test
    @DisplayName("Test error is present when subject does not exists")
    void testErrorIsPresentWhenSubjectDoesNotExist() {
        com.sleepypoem.testplatform.domain.entity.Test test = testFactory.create();
        when(subjectService.existsById(test.getSubject().getId())).thenReturn(false);
        Map<String,String> errors = testValidator.isValid(test);
        assertTrue(errors.containsKey("subject"));
        assertEquals("subject is null or does not exist", errors.get("subject"));
    }

    @Test
    @DisplayName("Test error is present when content is null")
    void testErrorIsPresentWhenContentIsNull() {
        com.sleepypoem.testplatform.domain.entity.Test test = testFactory.create();
        test.setContent(null);
        Map<String,String> errors = testValidator.isValid(test);
        assertTrue(errors.containsKey("content"));
        assertEquals("content is null", errors.get("content"));
    }

    @Test
    @DisplayName("Test error is present if questions is an empty array.")
    void testErrorIsPresentIfQuestionsIsAnEmptyArray() {
        com.sleepypoem.testplatform.domain.entity.Test test = testFactory.create();
        test.setContent("[]");
        Map<String, String> errors = testValidator.isValid(test);
        assertTrue(errors.containsKey("content -> questions"));
        assertEquals("question list is empty", errors.get("content -> questions"));
    }

    @Test
    @DisplayName("Test error is present if content cannot be parsed to a questions list")
    void testErrorIsPresentIfContentCannotBeParsedToQuestionsList() {
        com.sleepypoem.testplatform.domain.entity.Test test = testFactory.create();
        test.setContent("invalid content");
        Map<String, String> errors = testValidator.isValid(test);
        assertTrue(errors.containsKey("content"));
        assertEquals("content is invalid", errors.get("content"));

    }

    @Test
    @DisplayName("Test error is present when question validation fails")
    void testErrorIsPresentWhenQuestionValidationFails() {
        com.sleepypoem.testplatform.domain.entity.Test test = testFactory.create();
        test.setContent("[" +
                "{\"type\": \"OPEN\"," +
                " \"answer\": \"answer\"," +
                " \"description\" : \"description\"}]");
        when(questionValidator.isValid(any(Question.class))).thenReturn(Map.of("error", "error"));
        Map<String, String> errors = testValidator.isValid(test);
        assertTrue(errors.containsKey("content -> question 0"));
        assertEquals("Question 0 {error=error}", errors.get("content -> question 0"));
    }
}