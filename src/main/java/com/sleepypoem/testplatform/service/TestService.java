package com.sleepypoem.testplatform.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import com.sleepypoem.testplatform.domain.entity.Test;

@Service
public class TestService extends AbstractQueryableService<Long, Test>{
    public TestService(JpaRepository<Test, Long> repository, JpaSpecificationExecutor<Test> specificationExecutor) {
        super(repository, specificationExecutor);
    }
}
