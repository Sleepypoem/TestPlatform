package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import com.sleepypoem.testplatform.service.base.QueryService;
import com.sleepypoem.testplatform.service.utils.CustomSpecification;
import com.sleepypoem.testplatform.service.utils.QueryParser;
import com.sleepypoem.testplatform.service.utils.StatementDto;
import com.sleepypoem.testplatform.service.utils.StatementOperator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Objects;

public class AbstractQueryableService<ID, E extends BaseEntity<ID>> extends AbstractService<ID, E> implements QueryService<ID, E> {

    protected final JpaSpecificationExecutor<E> specificationExecutor;

    public AbstractQueryableService(JpaRepository<E, ID> repository, JpaSpecificationExecutor<E> specificationExecutor) {
        super(repository);
        this.specificationExecutor = specificationExecutor;
    }

    /**
     * The getBy function is a generic function that can be used to query the database for any
     * entity type. It takes in a String representing the query and returns an object of type Page&lt;T&gt;.
     * The getBy function uses QueryParser to parse the string into StatementDto objects, which are then chained together.
     *
     * @param query    query Parse the query into a list of statementDto objects
     * @param pageable pageable Specify the page number, size and sorting
     * @return A page&lt;t&gt;
     */
    @Override
    public Page<E> getBy(String query, Pageable pageable) {
        List<StatementDto> dtos = QueryParser.parse(query);
        return specificationExecutor.findAll(chainOperations(dtos), pageable);
    }

    /**
     * The chainOperations function takes a list of StatementDto objects and returns a Specification object.
     * The function iterates through the list of StatementDtos, creating new CustomSpecifications for each one.
     * If the current iteration is not the first, it will chain together previous specifications with either an 'AND' or
     * 'OR' operator depending on what was specified in the statement's operator field.
     *
     * @param specifications&lt;StatementDto&gt; specifications Build the specification
     * @return A specification object, which is then passed to the findAll method of the jpaSpecificationExecutor interface
     */
    private Specification<E> chainOperations(List<StatementDto> specifications) {
        Specification<E> specificationResult = null;
        for (StatementDto statementDto : specifications) {
            if (specificationResult == null) {
                Specification<E> first = new CustomSpecification<>(statementDto.getQuery());
                specificationResult = Specification.where(first);
                continue;
            }
            if (statementDto.getOperator() == null || statementDto.getOperator().equals(StatementOperator.AND)) {
                specificationResult = specificationResult.and(new CustomSpecification<>(statementDto.getQuery()));
            } else if (statementDto.getOperator() == StatementOperator.OR) {
                specificationResult = specificationResult.or(new CustomSpecification<>(statementDto.getQuery()));
            }
        }
        return Objects.requireNonNullElseGet(specificationResult, () -> Specification.where(null));
    }
}
