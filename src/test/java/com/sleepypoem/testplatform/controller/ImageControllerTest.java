package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.domain.dto.ImageDto;
import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.service.AbstractService;
import com.sleepypoem.testplatform.testutils.factories.impl.ImageFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    ImageController controller;

    @Mock
    AbstractService<Long, Image> service;

    ImageFactory factory;

    @BeforeEach
    void setUp() {
        controller = new ImageController(service);
        factory = new ImageFactory();
    }

    @Test
    @DisplayName("Test get all images")
    void getAllImages() {
        //arrange
        Page<Image> images = new PageImpl<>(factory.createList(5));
        when(service.getAll(any(Pageable.class))).thenReturn(images);
        //act
        ResponseEntity<PaginatedDto<ImageDto>> response = controller.all(PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Test get image by id")
    void getImageById() {
        //arrange
        Image image = factory.create();
        when(service.getOneById(any(Long.class))).thenReturn(image);
        //act
        ResponseEntity<ImageDto> response = controller.one(1L);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getOneById(any(Long.class));
    }

    @Test
    @DisplayName("Test create image")
    void createImage() {
        //arrange
        Image image = factory.create();
        when(service.create(any(Image.class))).thenReturn(image);
        //act
        ResponseEntity<ImageDto> response = controller.create(image);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).create(image);
    }

    @Test
    @DisplayName("Test update image")
    void updateImage() {
        //arrange
        Image image = factory.create();
        when(service.update(any(Long.class), any(Image.class))).thenReturn(image);
        //act
        ResponseEntity<ImageDto> response = controller.update(1L, image);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).update(any(Long.class), any(Image.class));
    }


    @Test
    @DisplayName("Test delete image")
    void deleteImage() {
        //arrange
        doNothing().when(service).delete(any(Long.class));
        //act
        ResponseEntity<String> response = controller.delete(1L);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(204), response.getStatusCode());
        assertNull(response.getBody());
        verify(service).delete(any(Long.class));
    }

    @Test
    @DisplayName("Test delete an image when error")
    void deleteImageWhenError() {
        //arrange
        doThrow(new RuntimeException("Error")).when(service).delete(any(Long.class));
        //act
        ResponseEntity<String> response = controller.delete(1L);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
        assertEquals("Error", response.getBody());
        verify(service).delete(any(Long.class));
    }

}