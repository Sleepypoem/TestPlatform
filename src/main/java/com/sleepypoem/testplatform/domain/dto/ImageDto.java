package com.sleepypoem.testplatform.domain.dto;

import lombok.Data;

@Data
public class ImageDto extends DtoWithTimestamps implements BaseDto<Long> {

    private Long id;

    private String name;

    private String format;

    private String path;

    private int size;

    private int width;

    private int height;
}
