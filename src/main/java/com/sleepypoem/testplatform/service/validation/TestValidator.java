package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.dto.Question;
import com.sleepypoem.testplatform.domain.entity.Subject;
import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.domain.entity.Test;
import com.sleepypoem.testplatform.domain.utils.QuestionParser;
import com.sleepypoem.testplatform.enums.TestStatus;
import com.sleepypoem.testplatform.service.SubjectService;
import com.sleepypoem.testplatform.service.TeacherService;

import java.util.*;

public class TestValidator implements IValidator<Test> {

    private final TeacherService teacherService;

    private final SubjectService subjectService;

    private final QuestionParser questionParser;

    private final IValidator<Question> questionValidator;

    public TestValidator(TeacherService teacherService, SubjectService subjectService, QuestionParser questionParser, IValidator<Question> questionValidator) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.questionParser = questionParser;
        this.questionValidator = questionValidator;
    }

    @Override
    public Map<String, String> isValid(Test element) {
        Map<String, String> errors = new HashMap<>();
        Teacher teacher = element.getTeacher();
        Subject subject = element.getSubject();

        if(element.getId() != null && element.getStatus() == TestStatus.SUBMITTED) {
            errors.put("status", "status is already submitted, it can't be modified");
            return errors;
        }

        if (teacher == null || !teacherService.existsById(teacher.getId())) {
            errors.put("teacher", "teacher is null or does not exist");
        }

        if(subject == null || !subjectService.existsById(subject.getId())) {
            errors.put("subject", "subject is null or does not exist");
        }

        if (element.getContent() == null) {
            errors.put("content", "content is null");
            return errors;
        }

        try {
            validateQuestions(element, errors);
        } catch (Exception e) {
            errors.put("content", "content is invalid");
        }

        return errors;
    }

    private void validateQuestions(Test test, Map<String, String> errors) {
        List<? super Question> questions = questionParser.parse(test.getContent());

        if(questions.isEmpty()) {
            errors.put("content -> questions", "question list is empty");
        }

        for (int i = 0; i < questions.size(); i++) {
            questionValidator.isValid((Question) questions.get(i));
            errors.put("content -> question " + i, "Question " + i + " " + questionValidator.isValid((Question) questions.get(i)).toString());
        }

    }


}
