package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.SubjectDto;
import com.sleepypoem.testplatform.domain.entity.Subject;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import com.sleepypoem.testplatform.testutils.factories.impl.SubjectFactory;
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
class SubjectControllerTest {

    SubjectController controller;

    @Mock
    AbstractQueryableService<Long, Subject> service;

    SubjectFactory factory;

    @BeforeEach
    void setUp() {
        controller = new SubjectController(service);
        factory = new SubjectFactory();
    }

    @Test
    @DisplayName("Test get all subjects")
    void getAllSubjects() {
        //arrange
        Page<Subject> subjects = new PageImpl<>(factory.createList(5));
        when(service.getAll(any(Pageable.class))).thenReturn(subjects);
        //act
        ResponseEntity<PaginatedDto<SubjectDto>> response = controller.all(PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Test get subject by id")
    void getSubjectById() {
        //arrange
        Subject subject = factory.create();
        when(service.getOneById(any(Long.class))).thenReturn(subject);
        //act
        ResponseEntity<SubjectDto> response = controller.one(1L);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getOneById(any(Long.class));
    }

    @Test
    @DisplayName("Test query subjects")
    void testQuerySubjectsWhenOk() {
        //arrange
        Page<Subject> subjects = new PageImpl<>(factory.createList(5));
        when(service.getBy(any(String.class), any(Pageable.class))).thenReturn(subjects);
        //act
        ResponseEntity<PaginatedDto<SubjectDto>> response = controller.search("test", PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getBy(any(String.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Test create a subject")
    void createSubject() {
        //arrange
        Subject subject = factory.create();
        when(service.create(any(Subject.class))).thenReturn(subject);
        //act
        ResponseEntity<SubjectDto> response = controller.create(subject);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).create(subject);
    }

    @Test
    @DisplayName("Test update a subject")
    void updateSubject() {
        //arrange
        Subject subject = factory.create();
        when(service.update(any(Long.class), any(Subject.class))).thenReturn(subject);
        //act
        ResponseEntity<SubjectDto> response = controller.update(1L, subject);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).update(any(Long.class), any(Subject.class));
    }


    @Test
    @DisplayName("Test delete a subject")
    void deleteSubject() {
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
    @DisplayName("Test delete a subject when error")
    void deleteSubjectWhenError() {
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