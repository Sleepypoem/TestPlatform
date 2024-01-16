package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TeacherValidator implements IValidator<Teacher> {

    private TeacherService teacherService;

    @Autowired
    @Lazy
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
    @Override
    public Map<String, String> isValid(Teacher element) {
        Map<String, String> errors = new HashMap<>();
        if (element.getFirstName() == null || element.getFirstName().isEmpty()) {
            errors.put("firstName", "First name is null or empty");
        }
        if (element.getLastName() == null || element.getLastName().isEmpty()) {
            errors.put("lastName", "Last name is null or empty");
        }
        if (element.getTeacherCode() == null || element.getTeacherCode() <= 0) {
            errors.put("teacherCode", "Teacher code is null or invalid");
        }
        if(element.getId() == null && element.getTeacherCode() != null && Boolean.TRUE.equals(teacherService.existsByTeacherCode(element.getTeacherCode()))) {
            errors.put("teacherCode", "Teacher code already exists");
        }
        return errors;
    }
}
