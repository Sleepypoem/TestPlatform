package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.config.MapperProvider;
import com.sleepypoem.testplatform.domain.dto.TeacherDto;
import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import com.sleepypoem.testplatform.domain.entity.base.EntityWithDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static com.sleepypoem.testplatform.constants.ResourceConstants.TEACHER_ROLES_PREFIX;

@Entity
@Getter
@Setter
@Table(name = "teachers")
public class Teacher extends User implements BaseEntity<Long>, EntityWithDto<TeacherDto> {

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
    @JoinColumn(name = "image_id")
    private Image image;

    @Override
    public TeacherDto toDto() {
        TeacherDto teacherDto = MapperProvider.getMapper().map(this, TeacherDto.class);
        teacherDto.setRoles(TEACHER_ROLES_PREFIX + id);
        return teacherDto;
    }
}
