package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.domain.dto.ImageDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    @Test
    void testDtoFieldAreRight() {
        //arrange
        Image image = new Image();
        image.setId(1L);
        image.setName("test");
        image.setPath("test");
        image.setFormat("test");
        image.setSize(100);
        image.setWidth(100);
        image.setHeight(100);
        //act
        ImageDto actual = image.toDto();
        //assert
        assertAll(
                () -> assertEquals(1L, actual.getId()),
                () -> assertEquals("test", actual.getName()),
                () -> assertEquals("test", actual.getPath()),
                () -> assertEquals("test", actual.getFormat()),
                () -> assertEquals(100, actual.getSize()),
                () -> assertEquals(100, actual.getWidth()),
                () -> assertEquals(100, actual.getHeight())
        );
    }

}