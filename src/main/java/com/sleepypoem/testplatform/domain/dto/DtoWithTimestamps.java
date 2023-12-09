package com.sleepypoem.testplatform.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DtoWithTimestamps {

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;
}
