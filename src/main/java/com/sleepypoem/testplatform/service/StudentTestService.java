package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.StudentTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class StudentTestService extends AbstractQueryableService<Long, StudentTest>{
    public StudentTestService(JpaRepository<StudentTest, Long> repository, JpaSpecificationExecutor<StudentTest> specificationExecutor) {
        super(repository, specificationExecutor);
    }
}
