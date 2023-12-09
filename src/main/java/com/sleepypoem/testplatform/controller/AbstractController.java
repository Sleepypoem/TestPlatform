package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.controller.base.Controller;
import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import com.sleepypoem.testplatform.service.AbstractService;

public class AbstractController<E extends BaseEntity<ID>, ID> extends AbstractReadOnlyController<E, ID> implements Controller<E, ID> {
    public AbstractController(AbstractService<ID, E> service) {
        super(service);
    }

    @Override
    public E createInternal(E entity) {
        return service.create(entity);
    }

    @Override
    public E updateInternal(ID id, E entity) {
        return service.update(id, entity);
    }

    @Override
    public void deleteInternal(ID id) {
        service.delete(id);
    }

    @Override
    public void deleteInternal(E entity) {
        service.delete(entity);
    }
}
