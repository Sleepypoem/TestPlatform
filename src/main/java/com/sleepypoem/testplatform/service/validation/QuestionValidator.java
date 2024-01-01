package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.dto.ImageQuestion;
import com.sleepypoem.testplatform.domain.dto.MultiChoiceQuestion;
import com.sleepypoem.testplatform.domain.dto.OpenChoiceQuestion;
import com.sleepypoem.testplatform.domain.dto.Question;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QuestionValidator implements IValidator<Question>{
    @Override
    public Map<String, String> isValid(Question element) {
        Map<String, String> errors = new java.util.HashMap<>();

        if(element.getAnswer() == null) {
            errors.put("answer", "answer is null or empty");
        }

        if(element.getDescription() == null) {
            errors.put("description", "description is null or empty");
        }

        if (element.getType() == null) {
            errors.put("type", "type is null");
            return errors;
        }

        switch (element.getType()) {
            case OPEN:
                validateSingleChoiceQuestion(element, errors);
                break;
            case MULTIPLE:
                validateMultipleChoiceQuestion(element, errors);
                break;
            case IMAGE:
                validateImageQuestion(element, errors);
                break;
        }
        return errors;
    }

    private void validateSingleChoiceQuestion(Question question, Map<String, String> errors) {
        OpenChoiceQuestion openChoiceQuestion = (OpenChoiceQuestion) question;
        if(openChoiceQuestion.getResponses() == null || openChoiceQuestion.getResponses().length == 0) {
            errors.put("responses", "responses are null or empty");
        }

    }

    private void validateImageQuestion(Question question, Map<String, String> errors) {
        ImageQuestion  imageQuestion = (ImageQuestion) question;
        if (imageQuestion.getImages() == null || imageQuestion.getImages().length == 0) {
            errors.put("images", "images are null or empty");
        }
    }

    private void validateMultipleChoiceQuestion(Question question, Map<String, String> errors) {
        MultiChoiceQuestion multiChoiceQuestion = (MultiChoiceQuestion) question;
        if (multiChoiceQuestion.getOptions() == null || multiChoiceQuestion.getOptions().length == 0) {
            errors.put("options", "options are null or empty");
        }
    }
}
