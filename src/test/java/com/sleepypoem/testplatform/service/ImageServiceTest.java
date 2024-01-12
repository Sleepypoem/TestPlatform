package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.exception.MyEntityNotFoundException;
import com.sleepypoem.testplatform.service.validation.DefaultValidator;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
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
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private JpaRepository<Image, Long> repository;

    private ImageService service;

    private SimpleFactory<Image> factory;

    @BeforeEach
    void setUp() {
        DefaultValidator<Image> validator = new DefaultValidator<>();
        service = new ImageService(repository, validator);
        factory = new ImageFactory();
    }

    @Test
    @DisplayName("Test create an image when ok")
    void testCreateImageWhenOk() {
       //arrange
        Image expected = factory.create();
        when(repository.save(expected)).thenReturn(expected);
        //act
        Image actual = service.create(expected);
        //assert
        assertFields(expected, actual);

        verify(repository).save(expected);
    }

    @Test
    @DisplayName("Test create an Image when image is null")
    void testCreateImageWhenImageIsNull() {
        assertThrows(NullPointerException.class, () -> service.create(null));
    }

    @Test
    @DisplayName("Test update an image when ok")
    void testUpdateImageWhenOk() {
        //arrange
        Image expected = factory.create();
        Long id = expected.getId();
        when(repository.saveAndFlush(expected)).thenReturn(expected);
        //act
        Image actual = service.update(id, expected);
        //assert
        assertFields(expected, actual);

        verify(repository).saveAndFlush(expected);
    }

    @Test
    @DisplayName("Test update an image when image is null")
    void testUpdateImageWhenImageIsNull() {
        assertThrows(NullPointerException.class, () -> service.update(1L, null));
    }

    @Test
    @DisplayName("Test update an image when id and imageId are not the same")
    void testUpdateImageWhenIdAndImageIdAreNotTheSame() {
        Image entity = factory.create();
        entity.setId(3L);
        assertThrows(IllegalArgumentException.class, () -> service.update(2L, entity));

    }

    @Test
    @DisplayName("Test delete an image by id when ok")
    void testDeleteImageByIdWhenOk() {
        //arrange
        Image entity = factory.create();
        Long id = entity.getId();
        //act
        service.delete(id);
        //assert
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Test delete an image by id when id is null")
    void testDeleteImageWhenOk() {
        //arrange
        Image entity = factory.create();
        //act
        service.delete(entity);
        //assert
        verify(repository).delete(entity);
    }

    @Test
    @DisplayName("Test get an image by id when ok")
    void testGetImageByIdWhenOk() {
        //arrange
        Image actual = factory.create();
        Long id = actual.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.of(actual));
        //act
        Image expected = service.getOneById(id);
        //assert
        assertFields(actual, expected);

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get an image by id when entity not found")
    void testGetImageByIdWhenEntityNotFound() {
        //arrange
        Image entity = factory.create();
        Long id = entity.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());
        //act
        //assert
        assertThrows(MyEntityNotFoundException.class, () -> service.getOneById(id));
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get all images when ok")
    void testGetAllImagesWhenOk() {
        //arrange
        List<Image> entityList = factory.createList(5);
        Page<Image> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(repository.findAll(pageable)).thenReturn(resultPage);
        //act
        Page<Image> actual = service.getAll(pageable);
        //assert
        for (Image image : actual.getContent()) {
            Image expected = entityList.get(entityList.indexOf(image));
            assertFields(expected, image);
        }
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Test count all images when ok")
    void testCountAllImagesWhenOk() {
        //arrange
        int count = 5;
        when(repository.count()).thenReturn((long) count);
        //act
        long actual = service.count();
        //assert
        assertEquals(count, actual);
        verify(repository).count();
    }

    void assertFields(Image actual, Image expected) {
        assertAll(
                () -> assertNotNull(expected),
                () -> assertEquals(actual.getId(), expected.getId()),
                () -> assertEquals(actual.getName(), expected.getName()),
                () -> assertEquals(actual.getPath(), expected.getPath()),
                () -> assertEquals(actual.getFormat(), expected.getFormat()),
                () -> assertEquals(actual.getSize(), expected.getSize()),
                () -> assertEquals(actual.getWidth(), expected.getWidth()),
                () -> assertEquals(actual.getHeight(), expected.getHeight()),
                () -> assertEquals(actual.getCreatedAt(), expected.getCreatedAt()),
                () -> assertEquals(actual.getUpdatedAt(), expected.getUpdatedAt())
        );
    }


}