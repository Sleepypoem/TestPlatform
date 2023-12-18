package com.sleepypoem.testplatform.domain.dto;

import com.sleepypoem.testplatform.domain.entity.Image;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherDto extends DtoWithTimestamps implements BaseDto<Long> {

    private Long id;

    private String firstName;

    private String lastName;

    private Long teacherCode;

    private String roles;

    private ImageDto image;
}
