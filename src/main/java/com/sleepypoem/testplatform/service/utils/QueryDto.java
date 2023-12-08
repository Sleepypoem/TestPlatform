package com.sleepypoem.testplatform.service.utils;

import lombok.Data;

@Data
public class QueryDto {

    private String queryName;

    private String queryValue;

    private QueryOperator queryOperator;
}
