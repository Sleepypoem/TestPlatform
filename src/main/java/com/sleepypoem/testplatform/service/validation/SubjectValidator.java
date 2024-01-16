package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Subject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SubjectValidator implements IValidator<Subject> {
    @Override
    public Map<String, String> isValid(Subject element) {
        Map<String, String> errors = new HashMap<>();
        if (element.getName() == null || element.getName().isEmpty()) {
            errors.put("name", "name is null or empty");
        }

        return errors;
    }
}
