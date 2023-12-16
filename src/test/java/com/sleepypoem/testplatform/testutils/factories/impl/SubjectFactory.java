package com.sleepypoem.testplatform.testutils.factories.impl;

import com.sleepypoem.testplatform.domain.entity.Subject;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
import com.sleepypoem.testplatform.testutils.random.RandomGenerator;

import java.time.LocalDateTime;

public class SubjectFactory implements SimpleFactory<Subject> {
    @Override
    public Subject create() {
        Subject subject = new Subject();
        subject.setId(RandomGenerator.getRandomLong(1, 999));
        subject.setName(RandomGenerator.getRandomString(10));
        subject.setDescription(RandomGenerator.getRandomString(20));
        subject.setCreatedAt(LocalDateTime.now());
        subject.setUpdatedAt(LocalDateTime.now());
        return subject;
    }
}
