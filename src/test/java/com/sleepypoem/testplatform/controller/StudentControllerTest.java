package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.StudentDto;
import com.sleepypoem.testplatform.domain.entity.Student;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
import com.sleepypoem.testplatform.testutils.factories.impl.StudentFactory;
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
class StudentControllerTest {
    StudentController controller;

    @Mock
    AbstractQueryableService<Long, Student> service;

    StudentFactory factory;

    @BeforeEach
    void setUp() {
        controller = new StudentController(service);
        factory = new StudentFactory();
    }

    @Test
    @DisplayName("Test get all students")
    void getAllStudents() {
        //arrange
        Page<Student> students = new PageImpl<>(factory.createList(5));
        when(service.getAll(any(Pageable.class))).thenReturn(students);
        //act
        ResponseEntity<PaginatedDto<StudentDto>> response = controller.all(PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Test get student by id")
    void getStudentById() {
        //arrange
        Student student = factory.create();
        when(service.getOneById(any(Long.class))).thenReturn(student);
        //act
        ResponseEntity<StudentDto> response = controller.one(1L);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getOneById(any(Long.class));
    }

    @Test
    @DisplayName("Test query students")
    void testQueryStudentsWhenOk() {
        //arrange
        Page<Student> students = new PageImpl<>(factory.createList(5));
        when(service.getBy(any(String.class), any(Pageable.class))).thenReturn(students);
        //act
        ResponseEntity<PaginatedDto<StudentDto>> response = controller.search("test", PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getBy(any(String.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Test create a student")
    void createStudent() {
        //arrange
        Student student = factory.create();
        when(service.create(any(Student.class))).thenReturn(student);
        //act
        ResponseEntity<StudentDto> response = controller.create(student);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).create(student);
    }

    @Test
    @DisplayName("Test update a student")
    void updateStudent() {
        //arrange
        Student student = factory.create();
        when(service.update(any(Long.class), any(Student.class))).thenReturn(student);
        //act
        ResponseEntity<StudentDto> response = controller.update(1L, student);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).update(any(Long.class), any(Student.class));
    }


    @Test
    @DisplayName("Test delete a student")
    void deleteStudent() {
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
    @DisplayName("Test delete a student when error")
    void deleteStudentWhenError() {
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