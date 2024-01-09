package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Image;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ImageValidator implements IValidator<Image>{

    @Override
    public Map<String, String> isValid(Image element) {
        Map<String, String> errors = new HashMap<>();
        if(element.getName() == null) {
            errors.put("name", "name is null");
        }
        if(element.getFormat() == null) {
            errors.put("format", "format is null");
        }
        if(element.getPath() == null) {
            errors.put("path", "path is null");
        }

        if(element.getSize() <= 0) {
            errors.put("size", "size is invalid");
        }

        if(element.getWidth() < 250 || element.getWidth() > 1024) {
            errors.put("width", "width is invalid, width must be between 250px and 1024px");
        }

        if(element.getHeight() < 250 || element.getHeight() > 1024) {
            errors.put("height", "height is invalid, height must be between 250px and 1024px");
        }

        return errors;
    }
}
