package com.sleepypoem.testplatform.service.base;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryService<ID, E extends BaseEntity<ID>> {

    Page<E> getBy(String query, Pageable pageable);
}
