package com.sleepypoem.testplatform.service.utils;

import com.sleepypoem.testplatform.exception.MyInternalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryParserTest {

    @Test
    @DisplayName("Test if we can get a QueryDto from a string by parsing it.")
    void testParseSingleQuery() {
        String query = "name=test";
        QueryDto result = QueryParser.parseQuery(query);
        assertEquals("name", result.getQueryName());
        assertEquals("test", result.getQueryValue());
        assertEquals( QueryOperator.EQ, result.getQueryOperator());
    }

    @Test
    @DisplayName("Test if we can get a list of StatementDto from a string by parsing it.")
    void testParseMultiQuery() {
        String query = "name=test;ANDage=20";
        List<StatementDto> result = QueryParser.parse(query);
        assertAll(
                () -> assertNull(result.get(0).getOperator()),
                () -> assertEquals("name", result.get(0).getQuery().getQueryName()),
                () -> assertEquals("test", result.get(0).getQuery().getQueryValue()),
                () -> assertEquals(QueryOperator.EQ, result.get(0).getQuery().getQueryOperator()),
                () -> assertEquals(StatementOperator.AND ,result.get(1).getOperator()),
                () -> assertEquals("age", result.get(1).getQuery().getQueryName()),
                () -> assertEquals("20", result.get(1).getQuery().getQueryValue()),
                () -> assertEquals(QueryOperator.EQ, result.get(1).getQuery().getQueryOperator())
        );

    }

    @Test
    @DisplayName("Test if we get an exception when the string to be parsed does not contain any operators.")
    void testExceptionWhenNoOperator() {
        String query = "nametest";
        var ex =assertThrows(MyInternalException.class, () -> QueryParser.parseQuery(query));
        assertEquals("Invalid query format, operator not found", ex.getMessage());
    }



}