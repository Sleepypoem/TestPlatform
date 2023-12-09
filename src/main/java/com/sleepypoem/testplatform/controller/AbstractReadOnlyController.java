package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.controller.base.ReadOnlyController;
import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import com.sleepypoem.testplatform.service.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AbstractReadOnlyController<E extends BaseEntity<ID>, ID> implements ReadOnlyController<E, ID> {

    protected AbstractService<ID, E> service;

    public AbstractReadOnlyController(AbstractService<ID, E> service) {
        this.service = service;
    }

    @Override
    public Page<E> getAllInternal(Pageable pageable) {
        return service.getAll(pageable);
    }

    @Override
    public E getByIdInternal(ID id) {
        return service.getOneById(id);
    }
}
