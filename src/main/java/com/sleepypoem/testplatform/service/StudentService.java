package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends AbstractQueryableService<Long, Student>{
    public StudentService(JpaRepository<Student, Long> repository, JpaSpecificationExecutor<Student> specificationExecutor) {
        super(repository, specificationExecutor);
    }
}
