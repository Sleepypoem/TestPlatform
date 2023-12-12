package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends AbstractService<Long, Image>{
    public ImageService(JpaRepository<Image, Long> repository) {
        super(repository);
    }
}
