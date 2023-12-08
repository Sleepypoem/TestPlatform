package com.sleepypoem.testplatform.service.base;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReadOnlyService<ID, E extends BaseEntity<ID>> {

    E getOneById(ID id);


    Page<E> getAll(Pageable pageable);

    Long count();
}
