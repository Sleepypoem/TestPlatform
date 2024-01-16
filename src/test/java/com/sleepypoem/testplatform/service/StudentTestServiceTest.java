package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.domain.entity.Student;
import com.sleepypoem.testplatform.domain.entity.StudentTest;
import com.sleepypoem.testplatform.exception.MyEntityNotFoundException;
import com.sleepypoem.testplatform.exception.MyValidationException;
import com.sleepypoem.testplatform.service.validation.DefaultValidator;
import com.sleepypoem.testplatform.service.validation.StudentTestValidator;
import com.sleepypoem.testplatform.service.validation.StudentValidator;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class StudentTestServiceTest {


    @Mock
    private JpaRepository<StudentTest, Long> repository;

    @Mock
    private JpaSpecificationExecutor<StudentTest> specificationExecutor;

    private StudentTestService service;

    private SimpleFactory<StudentTest> factory;

    @BeforeEach
    void setUp() {
        DefaultValidator<StudentTest> validator = new DefaultValidator<>();
        service = new StudentTestService(repository, specificationExecutor, validator);
        factory = new StudentTestFactory();
    }

    @Test
    @DisplayName("Test create a student test when ok")
    void testCreateStudentTestWhenOk() {
        //arrange
        StudentTest expected = factory.create();
        when(repository.save(expected)).thenReturn(expected);
        //act
        StudentTest actual = service.create(expected);
        //assert
        assertFields(expected, actual);

        verify(repository).save(expected);
    }

    @Test
    @DisplayName("Test create a Student test when student test is null")
    void testCreateStudentTestWhenStudentTestIsNull() {
        assertThrows(NullPointerException.class, () -> service.create(null));
    }

    @Test
    @DisplayName("Test update a student test when ok")
    void testUpdateStudentTestWhenOk() {
        //arrange
        StudentTest expected = factory.create();
        Long id = expected.getId();
        when(repository.saveAndFlush(expected)).thenReturn(expected);
        //act
        StudentTest actual = service.update(id, expected);
        //assert
        assertFields(expected, actual);

        verify(repository).saveAndFlush(expected);
    }

    @Test
    @DisplayName("Test update a student test when student test is null")
    void testUpdateStudentTestWhenStudentTestIsNull() {
        assertThrows(NullPointerException.class, () -> service.update(1L, null));
    }

    @Test
    @DisplayName("Test update a student test when id and studentTestId are not the same")
    void testUpdateStudentTestWhenIdAndStudentTestIdAreNotTheSame() {
        StudentTest entity = factory.create();
        entity.setId(3L);
        assertThrows(IllegalArgumentException.class, () -> service.update(2L, entity));

    }

    @Test
    @DisplayName("Test delete a student test by id when ok")
    void testDeleteStudentTestByIdWhenOk() {
        //arrange
        StudentTest entity = factory.create();
        Long id = entity.getId();
        //act
        service.delete(id);
        //assert
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Test delete a student test by id when id is null")
    void testDeleteStudentTestWhenOk() {
        //arrange
        StudentTest entity = factory.create();
        //act
        service.delete(entity);
        //assert
        verify(repository).delete(entity);
    }

    @Test
    @DisplayName("Test get a student test by id when ok")
    void testGetStudentTestByIdWhenOk() {
        //arrange
        StudentTest actual = factory.create();
        Long id = actual.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.of(actual));
        //act
        StudentTest expected = service.getOneById(id);
        //assert
        assertFields(actual, expected);

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get a student test by id when entity not found")
    void testGetStudentTestByIdWhenEntityNotFound() {
        //arrange
        StudentTest entity = factory.create();
        Long id = entity.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());
        //act
        //assert
        assertThrows(MyEntityNotFoundException.class, () -> service.getOneById(id));
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get all student tests when ok")
    void testGetAllStudentTestsWhenOk() {
        //arrange
        List<StudentTest> entityList = factory.createList(5);
        Page<StudentTest> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(repository.findAll(pageable)).thenReturn(resultPage);
        //act
        Page<StudentTest> actual = service.getAll(pageable);
        //assert
        for (StudentTest studentTest : actual.getContent()) {
            StudentTest expected = entityList.get(entityList.indexOf(studentTest));
            assertFields(expected, studentTest);
        }
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Test count all student tests when ok")
    void testCountAllStudentTestsWhenOk() {
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
        List<StudentTest> entityList = factory.createList(5);
        Page<StudentTest> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(specificationExecutor.findAll(any(Specification.class), eq(pageable))).thenReturn(resultPage);
        //act
        service.getBy("id=1;ORname=test;ANDdescription=test", pageable);
        //assert
        verify(specificationExecutor).findAll(any(Specification.class), eq(pageable));

    }

    @Test
    @DisplayName("Test exists method when ok")
    void testExistsMethodWhenOk() {
        //arrange
        StudentTest entity = factory.create();
        Long id = entity.getId();
        when(repository.existsById(id)).thenReturn(true);
        //act
        boolean actual = service.existsById(id);
        //assert
        assertTrue(actual);
        verify(repository).existsById(id);
    }

    @Test
    @DisplayName("Test exists method when entity not found")
    void testExistsMethodWhenEntityNotFound() {
        //arrange
        StudentTest entity = factory.create();
        Long id = entity.getId();
        when(repository.existsById(id)).thenReturn(false);
        //act
        //assert
        assertFalse(service.existsById(id));
        verify(repository).existsById(id);
    }

    @Test
    @DisplayName("Test exception is thrown when validation fails in create method")
    void testValidationFailsInCreateMethod() {
        StudentTest studentTest = factory.create();
        StudentTestValidator validatorMock = mock(StudentTestValidator.class);
        service.setValidator(validatorMock);
        when(validatorMock.isValid(any(StudentTest.class))).thenReturn(Map.of("test", "test"));
        var ex = assertThrows(MyValidationException.class, () -> service.create(studentTest));
        String expectedMessage = """
                The following errors were found during validation : {
                Field: test || Error: test
                }""";
        assertEquals(expectedMessage, ex.getMessage());
        verify(validatorMock).isValid(studentTest);
    }

    @Test
    @DisplayName("Test exception is thrown when validation fails in update method")
    void testValidationFailsInUpdateMethod() {
        StudentTest studentTest = factory.create();
        Long id = studentTest.getId();
        StudentTestValidator validatorMock = mock(StudentTestValidator.class);
        service.setValidator(validatorMock);
        when(validatorMock.isValid(any(StudentTest.class))).thenReturn(Map.of("test", "test"));
        var ex = assertThrows(MyValidationException.class, () -> service.update(id, studentTest));
        String expectedMessage = """
                The following errors were found during validation : {
                Field: test || Error: test
                }""";
        assertEquals(expectedMessage, ex.getMessage());
        verify(validatorMock).isValid(studentTest);
    }

    void assertFields(StudentTest actual, StudentTest expected) {
        assertAll(
                () -> assertNotNull(expected),
                () -> assertEquals(actual.getId(), expected.getId()),
                () -> assertEquals(actual.getAnswers(), expected.getAnswers()),
                () -> assertEquals(actual.getScore(), expected.getScore()),
                () -> assertEquals(actual.getStatus(), expected.getStatus()),
                () -> assertNotNull(expected.getStudent()),
                () -> assertNotNull(expected.getTest()),
                () -> assertEquals(actual.getCreatedAt(), expected.getCreatedAt()),
                () -> assertEquals(actual.getUpdatedAt(), expected.getUpdatedAt())
        );
    }

}