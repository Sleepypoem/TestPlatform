package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Test;
import com.sleepypoem.testplatform.service.validation.IValidator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class TestService extends AbstractQueryableService<Long, Test> {
    public TestService(JpaRepository<Test, Long> repository,
                       JpaSpecificationExecutor<Test> specificationExecutor,
                       IValidator<Test> validator) {

        super(repository, specificationExecutor);
        setValidator(validator);
    }

}
