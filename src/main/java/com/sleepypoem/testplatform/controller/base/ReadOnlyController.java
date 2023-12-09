package com.sleepypoem.testplatform.controller.base;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadOnlyController<E extends BaseEntity<ID>, ID> {

    Page<E> getAllInternal(Pageable pageable);

    E getByIdInternal(ID id);
}
