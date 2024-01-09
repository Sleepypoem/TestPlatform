package com.sleepypoem.testplatform.service.validation;

import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.testutils.factories.impl.ImageFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ImageValidatorTest {

    private ImageValidator imageValidator;

    private ImageFactory factory;

    @BeforeEach
    void setUp() {
        imageValidator = new ImageValidator();
        factory = new ImageFactory();
    }

    @Test
    @DisplayName("Test no error is present if image is valid")
    void testValidImage() {
        Image image = factory.create();
        Map<String, String> errors = imageValidator.isValid(image);
        System.out.println(errors);
        assertTrue(errors.isEmpty());
    }

    @Test
    @DisplayName("Assert error is present when image name is null")
    void testImageNameNull() {
        Image image = factory.create();
        image.setName(null);
        Map<String, String> errors = imageValidator.isValid(image);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("name"));
        assertEquals("name is null", errors.get("name"));
    }

    @Test
    @DisplayName("Assert error is present when format is null")
    void testImageFormatNull() {
        Image image = factory.create();
        image.setFormat(null);
        Map<String, String> errors = imageValidator.isValid(image);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("format"));
        assertEquals("format is null", errors.get("format"));
    }

    @Test
    @DisplayName("Test error is present when image path is null")
    void testImagePathNull() {
        Image image = factory.create();
        image.setPath(null);
        Map<String, String> errors = imageValidator.isValid(image);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("path"));
        assertEquals("path is null", errors.get("path"));
    }

    @Test
    @DisplayName("Assert error is present when image size is less or equal to zero")
    void testImageSizeInvalid() {
        Image image = factory.create();
        image.setSize(0);
        Map<String, String> errors = imageValidator.isValid(image);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("size"));
        assertEquals("size is invalid", errors.get("size"));
    }

    @Test
    @DisplayName("Test error is present when image width is invalid")
    void testImageWidthInvalid() {
        Image image = factory.create();
        image.setWidth(0);
        Map<String, String> errors = imageValidator.isValid(image);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("width"));
        assertEquals("width is invalid, width must be between 250px and 1024px", errors.get("width"));
    }

    @Test
    @DisplayName("Test error is present when image height is invalid")
    void testImageHeightInvalid() {
        Image image = factory.create();
        image.setHeight(0);
        Map<String, String> errors = imageValidator.isValid(image);
        assertFalse(errors.isEmpty());
        assertTrue(errors.containsKey("height"));
        assertEquals("height is invalid, height must be between 250px and 1024px", errors.get("height"));
    }
}