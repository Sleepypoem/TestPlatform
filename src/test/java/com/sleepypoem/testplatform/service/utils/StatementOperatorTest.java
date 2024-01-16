package com.sleepypoem.testplatform.service.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StatementOperatorTest {

    @Test
    @DisplayName("Test if we can get the symbol of an operator")
    void testGetTheSymbol() {
        StatementOperator statementOperator = StatementOperator.AND;
        assertEquals("AND", statementOperator.getOperator());
    }

    @Test
    @DisplayName("Test if we can get an operator by any of the symbols (AND, OR)")
    void testGetBySymbol() {
        StatementOperator statementOperator = StatementOperator.getByOperator("AND");
        assertEquals(StatementOperator.AND, statementOperator);
    }

    @Test
    @DisplayName("Test if we can get the symbols as an array.")
    void testGetSymbolsAsArrayList() {
        String[] operators = Arrays.stream(StatementOperator.values()).map(o -> o.operator).toArray(String[]::new);
        assertEquals("[AND, OR]", Arrays.toString(operators));
    }

    @Test
    @DisplayName("Test if we can find an operator inside a string.")
    void testIfWeCanGetAnOperatorInAString() {
        String[] operators = Arrays.stream(StatementOperator.values()).map(o -> o.operator).toArray(String[]::new);
        String query = "test=test;ANDname=test";
        String foundOperator = Arrays.stream(operators).filter(query::contains).findFirst().orElse("not found");
        assertEquals("AND", foundOperator);
    }

    @Test
    @DisplayName("Test exception is thrown when operator not found")
    void testExceptionIsThrownWhenOperatorNotFound() {
        String op = "test";
        assertThrows(IllegalArgumentException.class, () -> StatementOperator.getByOperator(op));
    }

}