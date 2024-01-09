package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.config.MapperProvider;
import com.sleepypoem.testplatform.domain.dto.TestDto;
import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import com.sleepypoem.testplatform.domain.entity.base.EntityWithDto;
import com.sleepypoem.testplatform.enums.TestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tests")
public class Test extends EntityWithTimestamps implements BaseEntity<Long>, EntityWithDto<TestDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    TestStatus status;

    @Override
    public TestDto toDto() {
        return MapperProvider.getMapper().map(this, TestDto.class);
    }
}
