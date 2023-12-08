package com.sleepypoem.testplatform.domain.entity.base;

import com.sleepypoem.testplatform.domain.dto.BaseDto;

public interface EntityWithDto <D extends BaseDto<?>>{

    D toDto();
}
