package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.controller.base.QueryableController;
import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AbstractQueryableController<E extends BaseEntity<ID>, ID> extends AbstractController<E, ID> implements QueryableController<E, ID> {

    protected AbstractQueryableService<ID, E> queryService;

    public AbstractQueryableController(AbstractQueryableService<ID, E> service) {
        super(service);
        this.queryService = service;
    }

    @Override
    public Page<E> queryInternal(String query, Pageable pageable) {
        return queryService.getBy(query, pageable);
    }
}
