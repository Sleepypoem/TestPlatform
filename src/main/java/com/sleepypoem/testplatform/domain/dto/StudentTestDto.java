package com.sleepypoem.testplatform.domain.dto;

import lombok.Data;

@Data
public class StudentTestDto extends DtoWithTimestamps implements BaseDto<Long> {

    private Long id;

    private String answers;

    private StudentDto student;

    private TestDto test;

    private Integer score;

    private Integer status;
}
