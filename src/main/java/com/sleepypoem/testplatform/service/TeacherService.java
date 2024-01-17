package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.service.validation.IValidator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class TeacherService extends AbstractQueryableService<Long, Teacher> {
    public TeacherService(JpaRepository<Teacher, Long> repository, JpaSpecificationExecutor<Teacher> specificationExecutor, IValidator<Teacher> validator) {
        super(repository, specificationExecutor);
        this.validator = validator;
    }

    public Boolean existsByTeacherCode(Long teacherCode) {
        return specificationExecutor.exists((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("teacherCode"), teacherCode));
    }

    @Override
    public Teacher update(Long id, Teacher entity) {
        Teacher previous = getOneById(id);
        entity.setTeacherCode(previous.getTeacherCode());
        return super.update(id, entity);
    }
}
