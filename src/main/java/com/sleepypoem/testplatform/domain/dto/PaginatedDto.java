package com.sleepypoem.testplatform.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PaginatedDto<D> {

    private Integer current;

    private String prev;

    private String next;

    private Long total;

    private List<D> content;
}
