package com.sleepypoem.testplatform.service.validation;

import java.util.Map;

public class DefaultValidator<T> implements IValidator<T> {

    @Override
    public Map<String, String> isValid(T element) {
        return Map.of();
    }
}
