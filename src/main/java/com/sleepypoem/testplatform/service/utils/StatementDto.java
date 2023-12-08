package com.sleepypoem.testplatform.service.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementDto {

    private QueryDto query;

    private StatementOperator operator;
}
