package com.sleepypoem.testplatform.repository;

import com.sleepypoem.testplatform.domain.entity.StudentTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentTestRepository extends JpaRepository<StudentTest, Long>, JpaSpecificationExecutor<StudentTest> {
}
