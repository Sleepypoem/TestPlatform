package com.sleepypoem.testplatform.testutils.factories.impl;

import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.domain.entity.Role;
import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
import com.sleepypoem.testplatform.testutils.random.RandomGenerator;

import java.time.LocalDateTime;
import java.util.Set;

public class TeacherFactory implements SimpleFactory<Teacher> {

    @Override
    public Teacher create() {
        Teacher teacher = new Teacher();
        teacher.setId(RandomGenerator.getRandomLong(1, 999));
        teacher.setFirstName(RandomGenerator.getRandomString(10));
        teacher.setLastName(RandomGenerator.getRandomString(10));
        teacher.setTeacherCode(RandomGenerator.getRandomLong(8,8));
        teacher.setRoles(Set.of(new Role()));
        teacher.setImage(new Image());
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());
        return teacher;
    }
}
