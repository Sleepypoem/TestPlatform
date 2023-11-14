package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.domain.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
public class Image extends EntityWithTimestamps implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String format;

    private String path;

    private int size;

    private int width;

    private int height;
}
