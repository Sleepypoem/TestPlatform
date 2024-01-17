package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.exception.MyEntityNotFoundException;
import com.sleepypoem.testplatform.exception.MyValidationException;
import com.sleepypoem.testplatform.service.validation.DefaultValidator;
import com.sleepypoem.testplatform.service.validation.TeacherValidator;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {


    @Mock
    private JpaRepository<Teacher, Long> repository;

    @Mock
    private JpaSpecificationExecutor<Teacher> specificationExecutor;

    private TeacherService service;

    private SimpleFactory<Teacher> factory;

    @BeforeEach
    void setUp() {
        DefaultValidator<Teacher> validator = new DefaultValidator<>();
        service = new TeacherService(repository, specificationExecutor, validator);
        factory = new TeacherFactory();
    }

    @Test
    @DisplayName("Test create a teacher when ok")
    void testCreateTeacherWhenOk() {
        //arrange
        Teacher expected = factory.create();
        when(repository.save(expected)).thenReturn(expected);
        //act
        Teacher actual = service.create(expected);
        //assert
        assertFields(expected, actual);

        verify(repository).save(expected);
    }

    @Test
    @DisplayName("Test create a Teacher when teacher is null")
    void testCreateTeacherWhenTeacherIsNull() {
        assertThrows(NullPointerException.class, () -> service.create(null));
    }

    @Test
    @DisplayName("Test update a teacher when ok")
    void testUpdateTeacherWhenOk() {
        //arrange
        Teacher expected = factory.create();
        Long id = expected.getId();
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(expected));
        when(repository.saveAndFlush(expected)).thenReturn(expected);
        //act
        Teacher actual = service.update(id, expected);
        //assert
        assertFields(expected, actual);

        verify(repository).saveAndFlush(expected);
    }

    @Test
    @DisplayName("Test update a teacher when teacher is null")
    void testUpdateTeacherWhenTeacherIsNull() {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(factory.create()));
        assertThrows(NullPointerException.class, () -> service.update(1L, null));
    }

    @Test
    @DisplayName("Test update a teacher when id and teacherId are not the same")
    void testUpdateTeacherWhenIdAndTeacherIdAreNotTheSame() {
        Teacher entity = factory.create();
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(entity));
        entity.setId(3L);
        assertThrows(IllegalArgumentException.class, () -> service.update(2L, entity));

    }

    @Test
    @DisplayName("Test delete a teacher by id when ok")
    void testDeleteTeacherByIdWhenOk() {
        //arrange
        Teacher entity = factory.create();
        Long id = entity.getId();
        //act
        service.delete(id);
        //assert
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Test delete a teacher by id when id is null")
    void testDeleteTeacherWhenOk() {
        //arrange
        Teacher entity = factory.create();
        //act
        service.delete(entity);
        //assert
        verify(repository).delete(entity);
    }

    @Test
    @DisplayName("Test get a teacher by id when ok")
    void testGetTeacherByIdWhenOk() {
        //arrange
        Teacher actual = factory.create();
        Long id = actual.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.of(actual));
        //act
        Teacher expected = service.getOneById(id);
        //assert
        assertFields(actual, expected);

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get a teacher by id when entity not found")
    void testGetTeacherByIdWhenEntityNotFound() {
        //arrange
        Teacher entity = factory.create();
        Long id = entity.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());
        //act
        //assert
        assertThrows(MyEntityNotFoundException.class, () -> service.getOneById(id));
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get all teachers when ok")
    void testGetAllTeachersWhenOk() {
        //arrange
        List<Teacher> entityList = factory.createList(5);
        Page<Teacher> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(repository.findAll(pageable)).thenReturn(resultPage);
        //act
        Page<Teacher> actual = service.getAll(pageable);
        //assert
        for (Teacher teacher : actual.getContent()) {
            Teacher expected = entityList.get(entityList.indexOf(teacher));
            assertFields(expected, teacher);
        }
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Test count all teachers when ok")
    void testCountAllTeachersWhenOk() {
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
        List<Teacher> entityList = factory.createList(5);
        Page<Teacher> resultPage = new PageImpl<>(entityList);
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
        Teacher entity = factory.create();
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
        Teacher entity = factory.create();
        Long id = entity.getId();
        when(repository.existsById(id)).thenReturn(false);
        //act
        //assert
        assertFalse(service.existsById(id));
        verify(repository).existsById(id);
    }

    @Test
    @DisplayName("Test exists by teacher code when ok")
    void testExistsByTeacherCodeWhenOk() {
        //arrange
        Teacher entity = factory.create();
        Long teacherCode = entity.getTeacherCode();
        when(specificationExecutor.exists(any(Specification.class))).thenReturn(true);
        //act
        boolean actual = service.existsByTeacherCode(teacherCode);
        //assert
        assertTrue(actual);
        verify(specificationExecutor).exists(any(Specification.class));
    }

    @Test
    @DisplayName("Test exists by teacher code when entity not found")
    void testExistsByTeacherCodeWhenEntityNotFound() {
        //arrange
        Teacher entity = factory.create();
        Long teacherCode = entity.getTeacherCode();
        when(specificationExecutor.exists(any(Specification.class))).thenReturn(false);
        //act
        //assert
        assertFalse(service.existsByTeacherCode(teacherCode));
        verify(specificationExecutor).exists(any(Specification.class));
    }

    @Test
    @DisplayName("Test exception is thrown when validation fails in create method")
    void testValidationFailsInCreateMethod() {
        Teacher teacher = factory.create();
        TeacherValidator validatorMock = mock(TeacherValidator.class);
        service.setValidator(validatorMock);
        when(validatorMock.isValid(any(Teacher.class))).thenReturn(Map.of("test", "test"));
        var ex = assertThrows(MyValidationException.class, () -> service.create(teacher));
        String expectedMessage = """
                The following errors were found during validation : {
                Field: test || Error: test
                }""";
        assertEquals(expectedMessage, ex.getMessage());
        verify(validatorMock).isValid(teacher);
    }

    @Test
    @DisplayName("Test exception is thrown when validation fails in update method")
    void testValidationFailsInUpdateMethod() {
        Teacher teacher = factory.create();
        Long id = teacher.getId();
        TeacherValidator validatorMock = mock(TeacherValidator.class);
        service.setValidator(validatorMock);
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(teacher));
        when(validatorMock.isValid(any(Teacher.class))).thenReturn(Map.of("test", "test"));
        var ex = assertThrows(MyValidationException.class, () -> service.update(id, teacher));
        String expectedMessage = """
                The following errors were found during validation : {
                Field: test || Error: test
                }""";
        assertEquals(expectedMessage, ex.getMessage());
        verify(validatorMock).isValid(teacher);
    }

    void assertFields(Teacher actual, Teacher expected) {
        assertAll(
                () -> assertNotNull(expected),
                () -> assertEquals(actual.getId(), expected.getId()),
                () -> assertEquals(actual.getFirstName(), expected.getFirstName()),
                () -> assertEquals(actual.getLastName(), expected.getLastName()),
                () -> assertEquals(actual.getTeacherCode(), expected.getTeacherCode()),
                () -> assertNotNull(expected.getImage()),
                () -> assertEquals(actual.getCreatedAt(), expected.getCreatedAt()),
                () -> assertEquals(actual.getUpdatedAt(), expected.getUpdatedAt())
        );
    }

}