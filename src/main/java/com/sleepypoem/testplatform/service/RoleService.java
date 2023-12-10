package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends AbstractQueryableService<Long, Role> {
    public RoleService(JpaRepository<Role, Long> repository, JpaSpecificationExecutor<Role> specificationExecutor) {
        super(repository, specificationExecutor);
    }
}
