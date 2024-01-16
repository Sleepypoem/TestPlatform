package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.TeacherDto;
import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import com.sleepypoem.testplatform.testutils.factories.impl.TeacherFactory;
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
class TeacherControllerTest {

    TeacherController controller;

    @Mock
    AbstractQueryableService<Long, Teacher> service;

    TeacherFactory factory;

    @BeforeEach
    void setUp() {
        controller = new TeacherController(service);
        factory = new TeacherFactory();
    }

    @Test
    @DisplayName("Test get all teachers")
    void getAllTeachers() {
        //arrange
        Page<Teacher> teachers = new PageImpl<>(factory.createList(5));
        when(service.getAll(any(Pageable.class))).thenReturn(teachers);
        //act
        ResponseEntity<PaginatedDto<TeacherDto>> response = controller.all(PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Test get teacher by id")
    void getTeacherById() {
        //arrange
        Teacher teacher = factory.create();
        when(service.getOneById(any(Long.class))).thenReturn(teacher);
        //act
        ResponseEntity<TeacherDto> response = controller.one(1L);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getOneById(any(Long.class));
    }

    @Test
    @DisplayName("Test query teachers")
    void testQueryTeachersWhenOk() {
        //arrange
        Page<Teacher> teachers = new PageImpl<>(factory.createList(5));
        when(service.getBy(any(String.class), any(Pageable.class))).thenReturn(teachers);
        //act
        ResponseEntity<PaginatedDto<TeacherDto>> response = controller.search("test", PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getBy(any(String.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Test create a teacher")
    void createTeacher() {
        //arrange
        Teacher teacher = factory.create();
        when(service.create(any(Teacher.class))).thenReturn(teacher);
        //act
        ResponseEntity<TeacherDto> response = controller.create(teacher);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).create(teacher);
    }

    @Test
    @DisplayName("Test update a teacher")
    void updateTeacher() {
        //arrange
        Teacher teacher = factory.create();
        when(service.update(any(Long.class), any(Teacher.class))).thenReturn(teacher);
        //act
        ResponseEntity<TeacherDto> response = controller.update(1L, teacher);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).update(any(Long.class), any(Teacher.class));
    }


    @Test
    @DisplayName("Test delete a teacher")
    void deleteTeacher() {
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
    @DisplayName("Test delete a teacher when error")
    void deleteTeacherWhenError() {
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