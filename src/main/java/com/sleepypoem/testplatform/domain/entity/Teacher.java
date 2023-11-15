package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.FetchType;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "teachers")
public class Teacher extends User implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_code", nullable = false, unique = true)
    private Long teacherCode;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "roles_teachers",
            joinColumns = @JoinColumn(name = "teacher_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name="image_id")
    private Image image;
}
