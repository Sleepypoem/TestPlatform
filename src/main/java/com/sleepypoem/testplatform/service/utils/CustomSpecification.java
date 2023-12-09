package com.sleepypoem.testplatform.service.utils;

import com.sleepypoem.testplatform.exception.MyInternalException;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;


public class CustomSpecification<E> implements Specification<E> {

    private final transient QueryDto query;

    public CustomSpecification(QueryDto query) {
        this.query = query;
    }

    @Override
    public Predicate toPredicate(@NonNull Root<E> root, @NonNull CriteriaQuery<?> criteriaQuery, @NonNull CriteriaBuilder builder) {

        return handleNestedCriteriaName(query.getQueryName(), query.getQueryValue(), root, builder);
    }

    /**
     * The handleNestedCriteriaName function is used to handle nested criteria names.
     * For example, if the query name is &quot;user.name&quot;, then this function will return a predicate that checks
     * for equality between the value of &quot;user.get(&quot;name&quot;)&quot; and queryValue
     *
     * @param queryName  Get the nested criteria name
     * @param queryValue Create the predicate
     * @param path       Get the nestedcriteriapath
     * @param builder    The CriteriaBuilder object to create the predicate
     * @return A predicate
     */
    private Predicate handleNestedCriteriaName(String queryName, String queryValue, Path<?> path, @NonNull CriteriaBuilder builder) {

        String[] nestedCriteria = queryName.split("\\.");
        Path<?> nestedCriteriaPath = path;
        for (String nestedCriteriaName : nestedCriteria) {
            try {
                nestedCriteriaPath = nestedCriteriaPath.get(nestedCriteriaName);
            } catch (Exception e) {
                throw new MyInternalException("Invalid criteria name: " + nestedCriteriaName);
            }
        }
        return toPredicateInternal(nestedCriteriaPath, queryValue, builder);
    }

    /**
     * The toPredicateInternal function is a helper function that takes in the path, queryValue, and criteriaBuilder
     * as parameters. It then uses a switch statement to determine which operation to perform based on the QueryOperator
     * enum value of the query object. The switch statement returns an instance of Predicate for each case. This function
     * is called by both the toPredicate and getPredicates functions below it.
     *
     * @param path       Get the field name of the entity
     * @param queryValue Compare the value of the query with a field in the database
     * @param builder    Build the predicate
     * @return A predicate, which is then used in the topredicate function
     * @docauthor Trelent
     */
    private Predicate toPredicateInternal(Path<?> path, String queryValue, @NonNull CriteriaBuilder builder) {
        try {
            Class<?> type = path.getJavaType();

            if(type == LocalDateTime.class) {
                return compareDate(path, queryValue, query.getQueryOperator(), builder);
            }
            return switch (query.getQueryOperator()) {

                case NEQ -> notEqualOperation(queryValue, path, builder);
                case GTE -> greaterThanOrEqualToOperation(queryValue, path, builder);
                case LTE -> lessThanOrEqualToOperation(queryValue, path, builder);
                case EQ -> equalOperation(queryValue, path, builder);
                case GT -> greaterThanOperation(queryValue, path, builder);
                case LT -> lessThanOperation(queryValue, path, builder);
            };
        } catch (IllegalArgumentException e) {
            if (e.getMessage().startsWith("Can't compare")) {
                throw new MyInternalException("Comparison error, " + query.getQueryName() + " is a composite type, you need to compare with its fields instead");
            } else throw e;
        }
    }

    private Predicate greaterThanOperation(String criteriaValue, Path<?> path, CriteriaBuilder builder) {
        return builder.greaterThan((Expression<String>) path, criteriaValue);
    }

    private Predicate lessThanOperation(String criteriaValue, Path<?> path, CriteriaBuilder builder) {
        return builder.lessThan((Expression<String>) path, criteriaValue);
    }


    private Predicate greaterThanOrEqualToOperation(String criteriaValue, Path<?> path, CriteriaBuilder builder) {
        return builder.greaterThanOrEqualTo((Expression<String>) path, criteriaValue);
    }


    private Predicate lessThanOrEqualToOperation(String criteriaValue, Path<?> path, CriteriaBuilder builder) {
        return builder.lessThanOrEqualTo((Expression<String>) path, criteriaValue);
    }


    private Predicate equalOperation(String criteriaValue, Path<?> path, CriteriaBuilder builder) {
        if (path.getJavaType() == String.class) {
            return builder.like((Expression<String>) path, criteriaValue);
        }
        return builder.equal(path, criteriaValue);
    }

    private Predicate notEqualOperation(String criteriaValue, Path<?> path, CriteriaBuilder builder) {
        return builder.notEqual(path, criteriaValue);
    }

    /**
     * Compares a date expression to a given value based on the provided operator.
     *
     * <p>This method parses the string value to a LocalDateTime and casts the
     * path expression to a LocalDateTime. It then returns a predicate built with
     * the CriteriaBuilder based on the operator.
     * @param path the date expression path
     * @param value the date value as a string
     * @param operator the comparison operator
     * @param builder the CriteriaBuilder object to create the predicate
     * @return a predicate for the date comparison
     */
    @SuppressWarnings("unchecked")
    private Predicate compareDate(Expression<?> path, String value, QueryOperator operator, CriteriaBuilder builder) {
        LocalDateTime convertedDate = LocalDateTime.parse(value);
        Expression<LocalDateTime> castedPath = (Expression<LocalDateTime>) path;
        return switch (operator) {
            case GT -> builder.greaterThan(castedPath, convertedDate);
            case LT -> builder.lessThan(castedPath, convertedDate);
            case GTE -> builder.greaterThanOrEqualTo(castedPath, convertedDate);
            case LTE -> builder.lessThanOrEqualTo(castedPath, convertedDate);
            case NEQ -> builder.notEqual(castedPath, convertedDate);
            case EQ -> builder.equal(castedPath, convertedDate);
        };
    }
}
