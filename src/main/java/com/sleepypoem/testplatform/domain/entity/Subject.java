package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.config.MapperProvider;
import com.sleepypoem.testplatform.domain.dto.SubjectDto;
import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import com.sleepypoem.testplatform.domain.entity.base.EntityWithDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "subjects")
public class Subject extends EntityWithTimestamps implements BaseEntity<Long>, EntityWithDto<SubjectDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Override
    public SubjectDto toDto() {
        return MapperProvider.getMapper().map(this, SubjectDto.class);
    }
}
