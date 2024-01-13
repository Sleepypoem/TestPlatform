package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Role;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RoleValidator implements IValidator<Role> {
    @Override
    public Map<String, String> isValid(Role element) {
        Map<String, String> errors = new HashMap<>();
        if(element.getName() == null || element.getName().isEmpty()){
            errors.put("name", "name is null or empty");
        }

        return errors;
    }
}
