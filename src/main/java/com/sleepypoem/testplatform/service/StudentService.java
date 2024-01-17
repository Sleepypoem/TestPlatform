package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Student;
import com.sleepypoem.testplatform.service.validation.IValidator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class StudentService extends AbstractQueryableService<Long, Student> {
    public StudentService(JpaRepository<Student, Long> repository, JpaSpecificationExecutor<Student> specificationExecutor, IValidator<Student> validator) {
        super(repository, specificationExecutor);
        this.validator = validator;
    }

    public Boolean existsByStudentCode(Long studentCode) {
        return specificationExecutor.exists((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("studentCode"), studentCode));
    }

    @Override
    public Student update(Long id, Student entity) {
        Student previous = getOneById(id);
        entity.setStudentCode(previous.getStudentCode());
        return super.update(id, entity);
    }
}
