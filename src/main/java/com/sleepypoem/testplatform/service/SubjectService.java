package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class SubjectService extends AbstractQueryableService<Long, Subject> {
    public SubjectService(JpaRepository<Subject, Long> repository, JpaSpecificationExecutor<Subject> specificationExecutor) {
        super(repository, specificationExecutor);
    }
}
