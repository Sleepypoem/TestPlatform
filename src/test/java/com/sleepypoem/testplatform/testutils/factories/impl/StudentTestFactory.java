package com.sleepypoem.testplatform.testutils.factories.impl;

import com.sleepypoem.testplatform.domain.entity.StudentTest;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
import com.sleepypoem.testplatform.testutils.random.RandomGenerator;

import java.time.LocalDateTime;

public class StudentTestFactory implements SimpleFactory<StudentTest> {
    @Override
    public StudentTest create() {
        StudentTest studentTest = new StudentTest();
        studentTest.setId(RandomGenerator.getRandomLong(1,999));
        studentTest.setAnswers(RandomGenerator.getRandomString(10));
        studentTest.setStudent(new StudentFactory().create());
        studentTest.setTest(new TestFactory().create());
        studentTest.setStatus(RandomGenerator.getRandomInt(0,1));
        studentTest.setScore(RandomGenerator.getRandomInt(0,100));
        studentTest.setCreatedAt(LocalDateTime.now());
        studentTest.setUpdatedAt(LocalDateTime.now());
        return studentTest;
    }
}
