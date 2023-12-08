package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import com.sleepypoem.testplatform.exception.MyEntityNotFoundException;
import com.sleepypoem.testplatform.service.base.ReadOnlyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public class AbstractReadOnlyService<ID, E extends BaseEntity<ID>> implements ReadOnlyService<ID, E> {

    protected final JpaRepository<E, ID> repository;

    public AbstractReadOnlyService(JpaRepository<E, ID> repository) {
        this.repository = repository;
    }

    @Override
    public E getOneById(ID id) {
        return repository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Entity with id " + id + " not found."));
    }

    @Override
    public Page<E> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Long count() {
        return repository.count();
    }
}
