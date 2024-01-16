package com.sleepypoem.testplatform.testutils.factories.impl;

import com.sleepypoem.testplatform.domain.entity.Role;
import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
import com.sleepypoem.testplatform.testutils.random.RandomGenerator;

import java.time.LocalDateTime;
import java.util.List;

public class RoleFactory implements SimpleFactory<Role> {
    @Override
    public Role create() {
        Role role = new Role();
        role.setId(RandomGenerator.getRandomLong(1, 999));
        role.setName(RandomGenerator.getRandomString(10));
        role.setDescription(RandomGenerator.getRandomString(20));
        role.setTeachers(List.of(new Teacher()));
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        return role;
    }
}
