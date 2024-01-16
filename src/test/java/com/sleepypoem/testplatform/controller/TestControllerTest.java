package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.TestDto;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import com.sleepypoem.testplatform.testutils.factories.impl.TestFactory;
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
class TestControllerTest {

    TestController controller;

    @Mock
    AbstractQueryableService<Long, com.sleepypoem.testplatform.domain.entity.Test> service;

    TestFactory factory;

    @BeforeEach
    void setUp() {
        controller = new TestController(service);
        factory = new TestFactory();
    }

    @Test
    @DisplayName("Test get all tests")
    void getAllTests() {
        //arrange
        Page<com.sleepypoem.testplatform.domain.entity.Test> tests = new PageImpl<>(factory.createList(5));
        when(service.getAll(any(Pageable.class))).thenReturn(tests);
        //act
        ResponseEntity<PaginatedDto<TestDto>> response = controller.all(PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Test get test by id")
    void getTestById() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test test = factory.create();
        when(service.getOneById(any(Long.class))).thenReturn(test);
        //act
        ResponseEntity<TestDto> response = controller.one(1L);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getOneById(any(Long.class));
    }

    @Test
    @DisplayName("Test query tests")
    void testQueryTestsWhenOk() {
        //arrange
        Page<com.sleepypoem.testplatform.domain.entity.Test> tests = new PageImpl<>(factory.createList(5));
        when(service.getBy(any(String.class), any(Pageable.class))).thenReturn(tests);
        //act
        ResponseEntity<PaginatedDto<TestDto>> response = controller.search("test", PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getBy(any(String.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Test create a test")
    void createTest() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test test = factory.create();
        when(service.create(any(com.sleepypoem.testplatform.domain.entity.Test.class))).thenReturn(test);
        //act
        ResponseEntity<TestDto> response = controller.create(test);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).create(test);
    }

    @Test
    @DisplayName("Test update a test")
    void updateTest() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test test = factory.create();
        when(service.update(any(Long.class), any(com.sleepypoem.testplatform.domain.entity.Test.class))).thenReturn(test);
        //act
        ResponseEntity<TestDto> response = controller.update(1L, test);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).update(any(Long.class), any(com.sleepypoem.testplatform.domain.entity.Test.class));
    }


    @Test
    @DisplayName("Test delete a test")
    void deleteTest() {
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
    @DisplayName("Test delete a test when error")
    void deleteTestWhenError() {
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