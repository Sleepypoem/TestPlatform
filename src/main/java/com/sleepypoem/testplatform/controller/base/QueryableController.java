package com.sleepypoem.testplatform.controller.base;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;

public interface QueryableController<E extends BaseEntity<ID>, ID> extends Controller<E, ID>, QueryableReadOnlyController<E, ID> {
}
