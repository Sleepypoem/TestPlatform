package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Student;
import com.sleepypoem.testplatform.exception.MyEntityNotFoundException;
import com.sleepypoem.testplatform.service.validation.DefaultValidator;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {


    @Mock
    private JpaRepository<Student, Long> repository;

    @Mock
    private JpaSpecificationExecutor<Student> specificationExecutor;

    private StudentService service;

    private SimpleFactory<Student> factory;

    @BeforeEach
    void setUp() {
        DefaultValidator<Student> validator = new DefaultValidator<>();
        service = new StudentService(repository, specificationExecutor, validator);
        factory = new StudentFactory();
    }

    @Test
    @DisplayName("Test create a student when ok")
    void testCreateStudentWhenOk() {
        //arrange
        Student expected = factory.create();
        when(repository.save(expected)).thenReturn(expected);
        //act
        Student actual = service.create(expected);
        //assert
        assertFields(expected, actual);

        verify(repository).save(expected);
    }

    @Test
    @DisplayName("Test create a Student when student is null")
    void testCreateStudentWhenStudentIsNull() {
        assertThrows(NullPointerException.class, () -> service.create(null));
    }

    @Test
    @DisplayName("Test update a student when ok")
    void testUpdateStudentWhenOk() {
        //arrange
        Student expected = factory.create();
        Long id = expected.getId();
        when(repository.saveAndFlush(expected)).thenReturn(expected);
        //act
        Student actual = service.update(id, expected);
        //assert
        assertFields(expected, actual);

        verify(repository).saveAndFlush(expected);
    }

    @Test
    @DisplayName("Test update a student when student is null")
    void testUpdateStudentWhenStudentIsNull() {
        assertThrows(NullPointerException.class, () -> service.update(1L, null));
    }

    @Test
    @DisplayName("Test update a student when id and studentId are not the same")
    void testUpdateStudentWhenIdAndStudentIdAreNotTheSame() {
        Student entity = factory.create();
        entity.setId(3L);
        assertThrows(IllegalArgumentException.class, () -> service.update(2L, entity));

    }

    @Test
    @DisplayName("Test delete a student by id when ok")
    void testDeleteStudentByIdWhenOk() {
        //arrange
        Student entity = factory.create();
        Long id = entity.getId();
        //act
        service.delete(id);
        //assert
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Test delete a student by id when id is null")
    void testDeleteStudentWhenOk() {
        //arrange
        Student entity = factory.create();
        //act
        service.delete(entity);
        //assert
        verify(repository).delete(entity);
    }

    @Test
    @DisplayName("Test get a student by id when ok")
    void testGetStudentByIdWhenOk() {
        //arrange
        Student actual = factory.create();
        Long id = actual.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.of(actual));
        //act
        Student expected = service.getOneById(id);
        //assert
        assertFields(actual, expected);

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get a student by id when entity not found")
    void testGetStudentByIdWhenEntityNotFound() {
        //arrange
        Student entity = factory.create();
        Long id = entity.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());
        //act
        //assert
        assertThrows(MyEntityNotFoundException.class, () -> service.getOneById(id));
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get all students when ok")
    void testGetAllStudentsWhenOk() {
        //arrange
        List<Student> entityList = factory.createList(5);
        Page<Student> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(repository.findAll(pageable)).thenReturn(resultPage);
        //act
        Page<Student> actual = service.getAll(pageable);
        //assert
        for (Student student : actual.getContent()) {
            Student expected = entityList.get(entityList.indexOf(student));
            assertFields(expected, student);
        }
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Test count all students when ok")
    void testCountAllStudentsWhenOk() {
        //arrange
        int count = 5;
        when(repository.count()).thenReturn((long) count);
        //act
        long actual = service.count();
        //assert
        assertEquals(count, actual);
        verify(repository).count();
    }

    @Test
    @DisplayName("Test query when ok")
    void testQueryWhenOk() {
        //arrange
        List<Student> entityList = factory.createList(5);
        Page<Student> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(specificationExecutor.findAll(any(Specification.class), eq(pageable))).thenReturn(resultPage);
        //act
        service.getBy("id=1;ORname=test;ANDdescription=test", pageable);
        //assert
        verify(specificationExecutor).findAll(any(Specification.class), eq(pageable));

    }

    void assertFields(Student actual, Student expected) {
        assertAll(
                () -> assertNotNull(expected),
                () -> assertEquals(actual.getId(), expected.getId()),
                () -> assertEquals(actual.getFirstName(), expected.getFirstName()),
                () -> assertEquals(actual.getLastName(), expected.getLastName()),
                () -> assertEquals(actual.getGradeLevel(),  expected.getGradeLevel()),
                () -> assertEquals(actual.getStudentCode(), expected.getStudentCode()),
                () -> assertNotNull(expected.getImage()),
                () -> assertEquals(actual.getCreatedAt(), expected.getCreatedAt()),
                () -> assertEquals(actual.getUpdatedAt(), expected.getUpdatedAt())
        );
    }
}
