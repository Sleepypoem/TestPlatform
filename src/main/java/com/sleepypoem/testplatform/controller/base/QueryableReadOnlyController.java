package com.sleepypoem.testplatform.controller.base;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryableReadOnlyController<E extends BaseEntity<ID>, ID> {

    Page<E> queryInternal(String query, Pageable pageable);
}
