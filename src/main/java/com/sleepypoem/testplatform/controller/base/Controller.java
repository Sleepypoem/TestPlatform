package com.sleepypoem.testplatform.controller.base;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;

public interface Controller<E extends BaseEntity<ID>, ID> extends ReadOnlyController<E, ID> {

    E createInternal(E entity);


    E updateInternal(ID id, E entity);


    void deleteInternal(ID id);


    void deleteInternal(E entity);

}
