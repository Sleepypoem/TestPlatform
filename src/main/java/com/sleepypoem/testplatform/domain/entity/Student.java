package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="students")
public class Student extends User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="grade_level")
    private Integer gradeLevel;

    @Column(name="student_code", nullable = false, unique = true)
    private Long studentCode;

    @OneToOne
    @JoinColumn(name="image_id")
    private Image image;
}
