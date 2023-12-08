package com.sleepypoem.testplatform.service.base;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;

public interface Service<ID, E extends BaseEntity<ID>> extends ReadOnlyService<ID, E> {

    E create(E entity);

    E update(ID id, E entity);


    void delete(ID id);


    void delete(E entity);
}
