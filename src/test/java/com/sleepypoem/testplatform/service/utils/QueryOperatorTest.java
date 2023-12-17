package com.sleepypoem.testplatform.service.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class QueryOperatorTest {
    @Test
    @DisplayName("Test if we can get the symbol of an operator")
    void testGetTheSymbol() {
        QueryOperator queryOperator = QueryOperator.EQ;
        assertEquals("=", queryOperator.getSymbol());
    }

    @Test
    @DisplayName("Test if we can get an operator by any of the symbols (=, !=, >, <, >=, <=)")
    void testGetBySymbol() {
        QueryOperator queryOperator = QueryOperator.getBySymbol("=");
        assertEquals(QueryOperator.EQ, queryOperator);

    }

    @Test
    @DisplayName("Test if we can get the symbols as an array.")
    void testGetSymbolsAsArrayList() {
        String[] operators = Arrays.stream(QueryOperator.values()).map(o -> o.symbol).toArray(String[]::new);
        assertEquals("[!=, >=, <=, =, >, <]", Arrays.toString(operators));
    }

    @Test
    @DisplayName("Test if we can find an operator inside a string.")
    void testIfWeCanGetAnOperatorInAString() {
        String[] operators = Arrays.stream(QueryOperator.values()).map(o -> o.symbol).toArray(String[]::new);
        String query = "test!=test=";
        String foundOperator = Arrays.stream(operators).filter(query::contains).findFirst().orElse("not found");
        assertEquals("!=", foundOperator);
    }

    @Test
    @DisplayName("Test if exception is thrown when operator is not found")
    void testIfExceptionIsThrownWhenOperatorIsNotFound() {
        String op = "test";
        assertThrows(IllegalArgumentException.class, () -> QueryOperator.getBySymbol(op));
    }
}