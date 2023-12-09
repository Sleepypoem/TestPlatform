package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherService extends AbstractQueryableService<Long, Teacher> {
    public TeacherService(JpaRepository<Teacher, Long> repository, JpaSpecificationExecutor<Teacher> specificationExecutor) {
        super(repository, specificationExecutor);
    }
}
