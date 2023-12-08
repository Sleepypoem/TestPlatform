package com.sleepypoem.testplatform.service.utils;

import lombok.Getter;

@Getter
public enum QueryOperator {

    //Order is important!. when I use String.contains() it will return the first match, so the single symbols (like =, >, <)
    // should be at the end and the double symbols (like >=, <=, !=) should be at the beginning.

    NEQ("!="),

    GTE(">="),
    LTE("<="),
    EQ("="),
    GT(">"),
    LT("<");


    final String symbol;

    QueryOperator(String symbol) {
        this.symbol = symbol;
    }


    public static QueryOperator getBySymbol(String symbol) {
        for (QueryOperator queryOperator : QueryOperator.values()) {
            if (queryOperator.getSymbol().equals(symbol)) {
                return queryOperator;
            }
        }
        throw new IllegalArgumentException("CriteriaOperator with symbol " + symbol + " not found");
    }
}
