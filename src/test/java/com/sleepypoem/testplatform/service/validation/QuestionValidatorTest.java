package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.dto.ImageQuestion;
import com.sleepypoem.testplatform.domain.dto.MultiChoiceQuestion;
import com.sleepypoem.testplatform.domain.dto.OpenChoiceQuestion;
import com.sleepypoem.testplatform.enums.QuestionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionValidatorTest {

    OpenChoiceQuestion openChoiceQuestion;

    ImageQuestion imageQuestion;

    MultiChoiceQuestion multiChoiceQuestion;

    QuestionValidator questionValidator;

    @BeforeEach
    void setUp() {
        openChoiceQuestion = new OpenChoiceQuestion();
        imageQuestion = new ImageQuestion();
        multiChoiceQuestion = new MultiChoiceQuestion();
        questionValidator = new QuestionValidator();
    }

    @Test
    @DisplayName("Test error is present when answer is null")
    void testValidateOpenChoiceQuestionWithNullAnswer() {
        openChoiceQuestion.setType(QuestionType.OPEN);
        openChoiceQuestion.setDescription("description");
        openChoiceQuestion.setResponses(new String[]{"R1", "R2"});
        Map<String, String> errors = questionValidator.isValid(openChoiceQuestion);
        assertEquals(1, errors.size());
        assertEquals("answer is null or empty", errors.get("answer"));
    }

    @Test
    @DisplayName("Test error is present when description is null")
    void testValidateOpenChoiceQuestionWithNullDescription() {
        openChoiceQuestion.setType(QuestionType.OPEN);
        openChoiceQuestion.setAnswer("answer");
        openChoiceQuestion.setResponses(new String[]{"R1", "R2"});
        Map<String, String> errors = questionValidator.isValid(openChoiceQuestion);
        assertEquals(1, errors.size());
        assertEquals("description is null or empty", errors.get("description"));
    }

    @Test
    @DisplayName("Test error is present when question type is invalid")
    void testValidateQuestionWithInvalidQuestionType() {
        openChoiceQuestion.setType(null);
        openChoiceQuestion.setAnswer("answer");
        openChoiceQuestion.setDescription("description");
        openChoiceQuestion.setResponses(new String[]{"R1", "R2"});
        Map<String, String> errors = questionValidator.isValid(openChoiceQuestion);
        assertEquals(1, errors.size());
        assertEquals("type is null", errors.get("type"));

    }

    @Test
    @DisplayName("Test there is no error when open choice question is valid")
    void testValidateOpenChoiceQuestionWhenOk() {
        openChoiceQuestion.setType(QuestionType.OPEN);
        openChoiceQuestion.setAnswer("answer");
        openChoiceQuestion.setDescription("description");
        openChoiceQuestion.setResponses(new String[]{"R1", "R2"});
        Map<String, String> errors = questionValidator.isValid(openChoiceQuestion);
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Test error is present when open choice question responses are null")
    void testValidateOpenChoiceQuestionWhenInvalid() {
        openChoiceQuestion.setType(QuestionType.OPEN);
        openChoiceQuestion.setAnswer("answer");
        openChoiceQuestion.setDescription("description");
        Map<String, String> errors = questionValidator.isValid(openChoiceQuestion);
        assertEquals(1, errors.size());
        assertEquals("responses are null or empty", errors.get("responses"));
    }

    @Test
    @DisplayName("Test error is present when multi choice question options are null")
    void testValidateMultiChoiceQuestionWhenInvalid() {
        multiChoiceQuestion.setType(QuestionType.MULTIPLE);
        multiChoiceQuestion.setAnswer("answer");
        multiChoiceQuestion.setDescription("description");
        Map<String, String> errors = questionValidator.isValid(multiChoiceQuestion);
        assertEquals(1, errors.size());
        assertEquals("options are null or empty", errors.get("options"));
    }

    @Test
    @DisplayName("Test error is present when image question images are null")
    void testValidateImageQuestionWhenInvalid() {
        imageQuestion.setType(QuestionType.IMAGE);
        imageQuestion.setAnswer("answer");
        imageQuestion.setDescription("description");
        Map<String, String> errors = questionValidator.isValid(imageQuestion);
        assertEquals(1, errors.size());
        assertEquals("images are null or empty", errors.get("images"));
    }

}