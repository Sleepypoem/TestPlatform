package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Student;
import com.sleepypoem.testplatform.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StudentValidator implements IValidator<Student>{

    private StudentService studentService;

    @Autowired
    @Lazy
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public Map<String, String> isValid(Student element) {

        Map<String, String> errors = new HashMap<>();

        if (element.getFirstName() == null || element.getFirstName().isEmpty()) {
            errors.put("firstName", "First name is null or empty");
        }

        if (element.getLastName() == null || element.getLastName().isEmpty()) {
            errors.put("lastName", "Last name is null or empty");
        }

        if(element.getGradeLevel() <= 0) {
            errors.put("gradeLevel", "Grade level is less than or equal to 0");
        }

        if (element.getStudentCode() == null || element.getStudentCode() <= 0) {
            errors.put("studentCode", "Student code is null or invalid");
        }

        if(element.getStudentCode() != null && studentService.existsByStudentCode(element.getStudentCode())) {
            errors.put("studentCode", "Student code already exists");
        }
        return errors;
    }
}
