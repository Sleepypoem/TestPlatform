package com.sleepypoem.testplatform.testutils.factories.impl;

import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.domain.entity.Student;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
import com.sleepypoem.testplatform.testutils.random.RandomGenerator;

import java.time.LocalDateTime;

public class StudentFactory implements SimpleFactory<Student> {
    @Override
    public Student create() {
        Student student = new Student();
        student.setId(RandomGenerator.getRandomLong(1, 999));
        student.setFirstName(RandomGenerator.getRandomString(10));
        student.setLastName(RandomGenerator.getRandomString(10));
        student.setGradeLevel(RandomGenerator.getRandomInt(1, 12));
        student.setStudentCode(RandomGenerator.getRandomLong(8, 8));
        student.setImage(new Image());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        return student;
    }
}
