package com.sleepypoem.testplatform.domain.dto;

import lombok.Data;

@Data
public class StudentDto extends DtoWithTimestamps implements BaseDto<Long> {

    private Long id;

    private String firstName;

    private String lastName;

    private Long studentCode;

    private Integer gradeLevel;

    private ImageDto image;
}
