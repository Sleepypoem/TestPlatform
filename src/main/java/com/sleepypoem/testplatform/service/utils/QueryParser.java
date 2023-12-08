package com.sleepypoem.testplatform.service.utils;


import com.sleepypoem.testplatform.exception.MyInternalException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class QueryParser {

    private QueryParser() {
        throw new IllegalStateException(this.getClass().getName() + "is a utility class!");
    }

    public static List<StatementDto> parse(String query) {
        List<String> queries = Arrays.asList(query.split(";"));

        return queries.stream().map(QueryParser::parseStatement).toList();
    }

    public static StatementDto parseStatement(String query) {
        int statementOperatorMaxLength = Arrays.stream(StatementOperator.values()).mapToInt(op -> op.operator.length()).max().getAsInt();
        String[] statementOperators = Arrays.stream(StatementOperator.values()).map(op -> op.operator).toArray(String[]::new);
        Optional<String> foundOperator = Arrays.stream(statementOperators).filter(q -> query.substring(0, statementOperatorMaxLength).contains(q)).findFirst();
        StatementDto statementDto = new StatementDto();
        if (foundOperator.isPresent()) {
            statementDto.setQuery(parseQuery(query.substring(foundOperator.get().length())));
            statementDto.setOperator(StatementOperator.getByOperator(foundOperator.get()));
        } else {
            statementDto.setQuery(parseQuery(query));
        }

        return statementDto;
    }

    public static QueryDto parseQuery(String query) {
        QueryDto queryDto = new QueryDto();
        String[] operators = Arrays.stream(QueryOperator.values()).map(op -> op.symbol).toArray(String[]::new);
        String foundOperator = Arrays.stream(operators).filter(query::contains).findFirst().orElseThrow(() -> new MyInternalException("Invalid query format, operator not found"));

        queryDto.setQueryName(query.substring(0, query.indexOf(foundOperator)));
        queryDto.setQueryValue(query.substring(query.indexOf(foundOperator) + foundOperator.length()));
        queryDto.setQueryOperator(QueryOperator.getBySymbol(foundOperator));
        return queryDto;
    }
}
