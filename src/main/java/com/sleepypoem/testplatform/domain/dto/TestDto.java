package com.sleepypoem.testplatform.domain.dto;

import com.sleepypoem.testplatform.enums.TestStatus;
import lombok.Data;

@Data
public class TestDto extends DtoWithTimestamps implements BaseDto<Long>{

    private Long id;

    private String name;

    private String content;

    private SubjectDto subject;

    private TeacherDto teacher;

    private TestStatus status;
}
