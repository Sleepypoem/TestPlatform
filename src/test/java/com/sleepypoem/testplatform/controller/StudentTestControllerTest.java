package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.StudentTestDto;
import com.sleepypoem.testplatform.domain.entity.StudentTest;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import com.sleepypoem.testplatform.testutils.factories.impl.StudentTestFactory;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentTestControllerTest {

    StudentTestController controller;

    @Mock
    AbstractQueryableService<Long, StudentTest> service;

    StudentTestFactory factory;

    @BeforeEach
    void setUp() {
        controller = new StudentTestController(service);
        factory = new StudentTestFactory();
    }

    @Test
    @DisplayName("Test get all student tests")
    void getAllStudentTests() {
        //arrange
        Page<StudentTest> studentTests = new PageImpl<>(factory.createList(5));
        when(service.getAll(any(Pageable.class))).thenReturn(studentTests);
        //act
        ResponseEntity<PaginatedDto<StudentTestDto>> response = controller.all(PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Test get student test by id")
    void getStudentTestById() {
        //arrange
        StudentTest studentTest = factory.create();
        when(service.getOneById(any(Long.class))).thenReturn(studentTest);
        //act
        ResponseEntity<StudentTestDto> response = controller.one(1L);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getOneById(any(Long.class));
    }

    @Test
    @DisplayName("Test query student tests")
    void testQueryStudentTestsWhenOk() {
        //arrange
        Page<StudentTest> studentTests = new PageImpl<>(factory.createList(5));
        when(service.getBy(any(String.class), any(Pageable.class))).thenReturn(studentTests);
        //act
        ResponseEntity<PaginatedDto<StudentTestDto>> response = controller.search("test", PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getBy(any(String.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Test create a student test")
    void createStudentTest() {
        //arrange
        StudentTest studentTest = factory.create();
        when(service.create(any(StudentTest.class))).thenReturn(studentTest);
        //act
        ResponseEntity<StudentTestDto> response = controller.create(studentTest);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).create(studentTest);
    }

    @Test
    @DisplayName("Test update a student test")
    void updateStudentTest() {
        //arrange
        StudentTest studentTest = factory.create();
        when(service.update(any(Long.class), any(StudentTest.class))).thenReturn(studentTest);
        //act
        ResponseEntity<StudentTestDto> response = controller.update(1L, studentTest);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).update(any(Long.class), any(StudentTest.class));
    }


    @Test
    @DisplayName("Test delete a student test")
    void deleteStudentTest() {
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
    @DisplayName("Test delete a student test when error")
    void deleteStudentTestWhenError() {
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