package com.sleepypoem.testplatform.service;

import com.google.common.base.Preconditions;
import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import com.sleepypoem.testplatform.exception.MyValidationException;
import com.sleepypoem.testplatform.service.base.Service;
import com.sleepypoem.testplatform.service.validation.DefaultValidator;
import com.sleepypoem.testplatform.service.validation.IValidator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Objects;

@Setter
@Getter
public class AbstractService<ID, E extends BaseEntity<ID>> extends AbstractReadOnlyService<ID, E> implements Service<ID, E> {
    protected IValidator<E> validator;
    public AbstractService(JpaRepository<E, ID> repository) {
        super(repository);
        validator = new DefaultValidator<>();
    }

    @Override
    public E create(E entity) {
        checkNotNullEntity(entity);
        validate(entity);
        return repository.save(entity);
    }

    @Override
    public E update(ID id, E entity) {
        checkNotNullEntity(entity);
        Preconditions.checkNotNull(id, "URI id must not be null");
        Preconditions.checkArgument(Objects.equals(entity.getId(), id), "URI id and entity id must be equal");
        validate(entity);
        return repository.saveAndFlush(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(E entity) {
        repository.delete(entity);
    }

    private void checkNotNullEntity(E entity) {
        Preconditions.checkNotNull(entity, "Entity must not be null");
    }

    private void validate(E entity) {
        Map<String, String> errors = getValidator().isValid(entity);
        if (!errors.isEmpty()) {
            throw new MyValidationException(errors);
        }
    }
}
