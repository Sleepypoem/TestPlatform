package com.sleepypoem.testplatform.service.utils;

import lombok.Getter;

@Getter
public enum StatementOperator {
    AND("AND"),
    OR("OR");

    final String operator;

    StatementOperator(String operator) {
        this.operator = operator;
    }


    public static StatementOperator getByOperator(String operator) {
        for (StatementOperator so : StatementOperator.values()) {
            if (so.getOperator().equals(operator)) {
                return so;
            }
        }
        throw new IllegalArgumentException("Operator with symbol: " + operator + " not found");
    }
}
