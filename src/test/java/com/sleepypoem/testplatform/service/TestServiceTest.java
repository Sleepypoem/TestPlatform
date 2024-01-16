package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.exception.MyEntityNotFoundException;
import com.sleepypoem.testplatform.exception.MyValidationException;
import com.sleepypoem.testplatform.service.validation.DefaultValidator;
import com.sleepypoem.testplatform.service.validation.TestValidator;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
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
class TestServiceTest {


    @Mock
    private JpaRepository<com.sleepypoem.testplatform.domain.entity.Test, Long> repository;

    @Mock
    private JpaSpecificationExecutor<com.sleepypoem.testplatform.domain.entity.Test> specificationExecutor;

    private TestService service;

    private SimpleFactory<com.sleepypoem.testplatform.domain.entity.Test> factory;

    @BeforeEach
    void setUp() {
        DefaultValidator<com.sleepypoem.testplatform.domain.entity.Test> validator = new DefaultValidator<>();
        service = new TestService(repository, specificationExecutor, validator);
        service.setValidator(new DefaultValidator<>());
        factory = new TestFactory();
    }

    @Test
    @DisplayName("Test create a test when ok")
    void testCreateTestWhenOk() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test expected = factory.create();
        when(repository.save(expected)).thenReturn(expected);
        //act
        com.sleepypoem.testplatform.domain.entity.Test actual = service.create(expected);
        //assert
        assertFields(expected, actual);

        verify(repository).save(expected);
    }

    @Test
    @DisplayName("Test create a Test when test is null")
    void testCreateTestWhenTestIsNull() {
        assertThrows(NullPointerException.class, () -> service.create(null));
    }

    @Test
    @DisplayName("Test update a test when ok")
    void testUpdateTestWhenOk() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test expected = factory.create();
        Long id = expected.getId();
        when(repository.saveAndFlush(expected)).thenReturn(expected);
        //act
        com.sleepypoem.testplatform.domain.entity.Test actual = service.update(id, expected);
        //assert
        assertFields(expected, actual);

        verify(repository).saveAndFlush(expected);
    }

    @Test
    @DisplayName("Test update a test when test is null")
    void testUpdateTestWhenTestIsNull() {
        assertThrows(NullPointerException.class, () -> service.update(1L, null));
    }

    @Test
    @DisplayName("Test update a test when id and testId are not the same")
    void testUpdateTestWhenIdAndTestIdAreNotTheSame() {
        com.sleepypoem.testplatform.domain.entity.Test entity = factory.create();
        entity.setId(3L);
        assertThrows(IllegalArgumentException.class, () -> service.update(2L, entity));

    }

    @Test
    @DisplayName("Test delete a test by id when ok")
    void testDeleteTestByIdWhenOk() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test entity = factory.create();
        Long id = entity.getId();
        //act
        service.delete(id);
        //assert
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Test delete a test by id when id is null")
    void testDeleteTestWhenOk() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test entity = factory.create();
        //act
        service.delete(entity);
        //assert
        verify(repository).delete(entity);
    }

    @Test
    @DisplayName("Test get a test by id when ok")
    void testGetTestByIdWhenOk() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test actual = factory.create();
        Long id = actual.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.of(actual));
        //act
        com.sleepypoem.testplatform.domain.entity.Test expected = service.getOneById(id);
        //assert
        assertFields(actual, expected);

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get a test by id when entity not found")
    void testGetTestByIdWhenEntityNotFound() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test entity = factory.create();
        Long id = entity.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());
        //act
        //assert
        assertThrows(MyEntityNotFoundException.class, () -> service.getOneById(id));
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get all tests when ok")
    void testGetAllTestsWhenOk() {
        //arrange
        List<com.sleepypoem.testplatform.domain.entity.Test> entityList = factory.createList(5);
        Page<com.sleepypoem.testplatform.domain.entity.Test> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(repository.findAll(pageable)).thenReturn(resultPage);
        //act
        Page<com.sleepypoem.testplatform.domain.entity.Test> actual = service.getAll(pageable);
        //assert
        for (com.sleepypoem.testplatform.domain.entity.Test test : actual.getContent()) {
            com.sleepypoem.testplatform.domain.entity.Test expected = entityList.get(entityList.indexOf(test));
            assertFields(expected, test);
        }
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Test count all tests when ok")
    void testCountAllTestsWhenOk() {
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
    @DisplayName("Test exists method when ok")
    void testExistsMethodWhenOk() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test entity = factory.create();
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
        com.sleepypoem.testplatform.domain.entity.Test entity = factory.create();
        Long id = entity.getId();
        when(repository.existsById(id)).thenReturn(false);
        //act
        //assert
        assertFalse(service.existsById(id));
        verify(repository).existsById(id);
    }

    @Test
    @DisplayName("Test query when ok")
    void testQueryWhenOk() {
        //arrange
        List<com.sleepypoem.testplatform.domain.entity.Test> entityList = factory.createList(5);
        Page<com.sleepypoem.testplatform.domain.entity.Test> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(specificationExecutor.findAll(any(Specification.class), eq(pageable))).thenReturn(resultPage);
        //act
        service.getBy("id=1;ORname=test;ANDdescription=test", pageable);
        //assert
        verify(specificationExecutor).findAll(any(Specification.class), eq(pageable));

    }

    @Test
    @DisplayName("Test exception is thrown when validation fails in create method")
    void testValidationFailsInCreateMethod() {
        com.sleepypoem.testplatform.domain.entity.Test test = factory.create();
        TestValidator validatorMock = mock(TestValidator.class);
        service.setValidator(validatorMock);
        when(validatorMock.isValid(any(com.sleepypoem.testplatform.domain.entity.Test.class))).thenReturn(Map.of("test", "test"));
        var ex = assertThrows(MyValidationException.class, () -> service.create(test));
        String expectedMessage = """
                The following errors were found during validation : {
                Field: test || Error: test
                }""";
        assertEquals(expectedMessage, ex.getMessage());
        verify(validatorMock).isValid(test);
    }

    @Test
    @DisplayName("Test exception is thrown when validation fails in update method")
    void testValidationFailsInUpdateMethod() {
        com.sleepypoem.testplatform.domain.entity.Test test = factory.create();
        Long id = test.getId();
        TestValidator validatorMock = mock(TestValidator.class);
        service.setValidator(validatorMock);
        when(validatorMock.isValid(any(com.sleepypoem.testplatform.domain.entity.Test.class))).thenReturn(Map.of("test", "test"));
        var ex = assertThrows(MyValidationException.class, () -> service.update(id, test));
        String expectedMessage = """
                The following errors were found during validation : {
                Field: test || Error: test
                }""";
        assertEquals(expectedMessage, ex.getMessage());
        verify(validatorMock).isValid(test);
    }

    void assertFields(com.sleepypoem.testplatform.domain.entity.Test actual, com.sleepypoem.testplatform.domain.entity.Test expected) {
        assertAll(
                () -> assertNotNull(expected),
                () -> assertEquals(actual.getId(), expected.getId()),
                () -> assertEquals(actual.getName(), expected.getName()),
                () -> assertEquals(actual.getContent(), expected.getContent()),
                () -> assertNotNull(actual.getSubject()),
                () -> assertNotNull(actual.getTeacher()),
                () -> assertNotNull(actual.getStatus()),
                () -> assertEquals(actual.getCreatedAt(), expected.getCreatedAt()),
                () -> assertEquals(actual.getUpdatedAt(), expected.getUpdatedAt())
        );
    }

}