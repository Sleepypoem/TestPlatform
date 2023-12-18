package com.sleepypoem.testplatform.controller.utils;

import com.sleepypoem.testplatform.domain.dto.ImageDto;
import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.testutils.factories.impl.ImageFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaginatorTest {

    Paginator<ImageDto> paginator;

    ImageFactory imageFactory;

    @BeforeEach
    void setUp() {
        imageFactory = new ImageFactory();
        paginator = new Paginator<>("images?");
    }

    @Test
    @DisplayName("Test if we can get a paginated DTO with the correct fields")
    void testIfWeGetAPaginatedDtoWithTheCorrectFields() {
        //arrange
        Page<Image> testPage = new PageImpl<>(imageFactory.createList(10), PageRequest.of(0, 10), 10);
        //act
        PaginatedDto<ImageDto> expected = paginator.getPaginatedDtoFromPage(testPage);
        //assert
        assertAll(
                () -> assertEquals(0, expected.getCurrent()),
                () -> assertEquals(10, expected.getTotal()),
                () -> assertNull(expected.getPrev()),
                () -> assertNull(expected.getNext()),
                () -> assertEquals(10, expected.getContent().size())
        );
    }

    @Test
    @DisplayName("Test if we can get a paginated DTO with the correct fields from a DTO list")
    void testIfWeGetAPaginatedDtoWithTheCorrectFieldsFromADTOList() {
        //arrange
        List<Image> entityList = imageFactory.createList(10);
        List<ImageDto> dtoList = entityList.stream().map(Image::toDto).toList();
        //act
        PaginatedDto<ImageDto> expected = paginator.getPaginatedDtoFromList(0, 10, 10L, dtoList);
        //assert
        assertAll(
                () -> assertEquals(0, expected.getCurrent()),
                () -> assertEquals(10, expected.getTotal()),
                () -> assertNull(expected.getPrev()),
                () -> assertNull(expected.getNext()),
                () -> assertEquals(10, expected.getContent().size())
        );
    }

    @Test
    @DisplayName("Test if we can get the correct prev link")
    void testIfWeGetTheCorrectPrevLink() {
        //arrange
        Page<Image> testPage = new PageImpl<>(imageFactory.createList(10), PageRequest.of(1, 5), 10);
        //act
        PaginatedDto<ImageDto> expected = paginator.getPaginatedDtoFromPage(testPage);
        //assert
        assertEquals("/api/images?page=0&size=5&sort=UNSORTED", expected.getPrev());
        assertNull(expected.getNext());
    }

    @Test
    @DisplayName("Test if we can get the correct next link")
    void testIfWeGetTheCorrectNextLink() {
        //arrange
        Page<Image> testPage = new PageImpl<>(imageFactory.createList(10), PageRequest.of(0, 5), 10);
        //act
        PaginatedDto<ImageDto> expected = paginator.getPaginatedDtoFromPage(testPage);
        //assert
        assertEquals("/api/images?page=1&size=5&sort=UNSORTED", expected.getNext());
        assertNull(expected.getPrev());
    }

}