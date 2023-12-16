package com.sleepypoem.testplatform.testutils.factories.impl;

import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
import com.sleepypoem.testplatform.testutils.random.RandomGenerator;

import java.time.LocalDateTime;

public class ImageFactory implements SimpleFactory<Image> {
    @Override
    public Image create() {
        Image image = new Image();
        image.setId(RandomGenerator.getRandomLong(1, 999));
        image.setName(RandomGenerator.getRandomString(10));
        image.setPath(RandomGenerator.getRandomString(10));
        image.setFormat(RandomGenerator.getRandomString(3));
        image.setSize(RandomGenerator.getRandomInt(1, 999));
        image.setWidth(RandomGenerator.getRandomInt(1, 999));
        image.setHeight(RandomGenerator.getRandomInt(1, 999));
        image.setCreatedAt(LocalDateTime.now());
        image.setUpdatedAt(LocalDateTime.now());
        return image;
    }
}
