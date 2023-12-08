package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "student_test")
public class StudentTest extends EntityWithTimestamps implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answers;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "test_id")
    private Test test;

    private Integer score;

    private Integer status;
}
