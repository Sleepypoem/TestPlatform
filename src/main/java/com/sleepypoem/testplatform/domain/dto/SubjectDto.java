package com.sleepypoem.testplatform.domain.dto;

import lombok.Data;

@Data
public class SubjectDto extends DtoWithTimestamps implements BaseDto<Long> {

    private Long id;

    private String name;

    private String description;
}
