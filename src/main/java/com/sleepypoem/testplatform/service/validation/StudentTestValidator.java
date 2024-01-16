package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Student;
import com.sleepypoem.testplatform.domain.entity.StudentTest;
import com.sleepypoem.testplatform.domain.entity.Test;
import com.sleepypoem.testplatform.service.StudentService;
import com.sleepypoem.testplatform.service.TestService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StudentTestValidator implements IValidator<StudentTest> {

    private final StudentService studentService;

    private final TestService testService;


    public StudentTestValidator(StudentService studentService, TestService testService) {
        this.studentService = studentService;
        this.testService = testService;
    }

    @Override
    public Map<String, String> isValid(StudentTest element) {
        Map<String, String> errors = new HashMap<>();
        Student student = element.getStudent();
        Test test = element.getTest();

        if (element.getAnswers() == null) {
            errors.put("answers", "answers is null");
        }

        if (student == null || !studentService.existsById(student.getId())) {
            errors.put("student", "student is null or does not exist");
            return errors;
        }

        if (test == null || !testService.existsById(test.getId())) {
            errors.put("test", "test is null or does not exist");
        }
        return errors;
    }
}
