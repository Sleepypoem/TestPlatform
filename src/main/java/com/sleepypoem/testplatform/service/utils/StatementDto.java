package com.sleepypoem.testplatform.service.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatementDto {

    private QueryDto query;

    private StatementOperator operator;
}
