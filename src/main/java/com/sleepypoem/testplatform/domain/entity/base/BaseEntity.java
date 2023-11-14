package com.sleepypoem.testplatform.domain.entity.base;

import java.io.Serializable;

public interface BaseEntity<ID> extends Serializable {

    ID getId();
}
