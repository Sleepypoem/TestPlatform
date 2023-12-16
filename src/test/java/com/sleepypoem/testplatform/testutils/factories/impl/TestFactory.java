package com.sleepypoem.testplatform.testutils.factories.impl;

import com.sleepypoem.testplatform.domain.entity.Subject;
import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.domain.entity.Test;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
import com.sleepypoem.testplatform.testutils.random.RandomGenerator;

import java.time.LocalDateTime;

public class TestFactory implements SimpleFactory<Test> {
    @Override
    public Test create() {
        Test test = new Test();
        test.setId(RandomGenerator.getRandomLong(1, 999));
        test.setName(RandomGenerator.getRandomString(10));
        test.setContent(RandomGenerator.getRandomString(10));
        test.setSubject(new Subject());
        test.setTeacher(new Teacher());
        test.setCreatedAt(LocalDateTime.now());
        test.setUpdatedAt(LocalDateTime.now());
        return test;
    }
}
