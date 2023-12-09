package com.sleepypoem.testplatform.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sleepypoem.testplatform.config.MapperProvider;
import com.sleepypoem.testplatform.domain.dto.RoleDto;
import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import com.sleepypoem.testplatform.domain.entity.base.EntityWithDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends EntityWithTimestamps implements BaseEntity<Long>, EntityWithDto<RoleDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Teacher> teachers;

    @Override
    public RoleDto toDto() {
        return MapperProvider.getMapper().map(this, RoleDto.class);
    }
}
