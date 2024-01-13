package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Subject;
import com.sleepypoem.testplatform.exception.MyEntityNotFoundException;
import com.sleepypoem.testplatform.service.validation.DefaultValidator;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectServiceTest {


    @Mock
    private JpaRepository<Subject, Long> repository;

    @Mock
    private JpaSpecificationExecutor<Subject> specificationExecutor;

    private SubjectService service;

    private SimpleFactory<Subject> factory;

    @BeforeEach
    void setUp() {
        DefaultValidator<Subject> validator = new DefaultValidator<>();
        service = new SubjectService(repository, specificationExecutor, validator);
        factory = new SubjectFactory();
    }

    @Test
    @DisplayName("Test create a subject when ok")
    void testCreateSubjectWhenOk() {
        //arrange
        Subject expected = factory.create();
        when(repository.save(expected)).thenReturn(expected);
        //act
        Subject actual = service.create(expected);
        //assert
        assertFields(expected, actual);

        verify(repository).save(expected);
    }

    @Test
    @DisplayName("Test create a Subject when subject is null")
    void testCreateSubjectWhenSubjectIsNull() {
        assertThrows(NullPointerException.class, () -> service.create(null));
    }

    @Test
    @DisplayName("Test update a subject when ok")
    void testUpdateSubjectWhenOk() {
        //arrange
        Subject expected = factory.create();
        Long id = expected.getId();
        when(repository.saveAndFlush(expected)).thenReturn(expected);
        //act
        Subject actual = service.update(id, expected);
        //assert
        assertFields(expected, actual);

        verify(repository).saveAndFlush(expected);
    }

    @Test
    @DisplayName("Test update a subject when subject is null")
    void testUpdateSubjectWhenSubjectIsNull() {
        assertThrows(NullPointerException.class, () -> service.update(1L, null));
    }

    @Test
    @DisplayName("Test update a subject when id and subjectId are not the same")
    void testUpdateSubjectWhenIdAndSubjectIdAreNotTheSame() {
        Subject entity = factory.create();
        entity.setId(3L);
        assertThrows(IllegalArgumentException.class, () -> service.update(2L, entity));

    }

    @Test
    @DisplayName("Test delete a subject by id when ok")
    void testDeleteSubjectByIdWhenOk() {
        //arrange
        Subject entity = factory.create();
        Long id = entity.getId();
        //act
        service.delete(id);
        //assert
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Test delete a subject by id when id is null")
    void testDeleteSubjectWhenOk() {
        //arrange
        Subject entity = factory.create();
        //act
        service.delete(entity);
        //assert
        verify(repository).delete(entity);
    }

    @Test
    @DisplayName("Test get a subject by id when ok")
    void testGetSubjectByIdWhenOk() {
        //arrange
        Subject actual = factory.create();
        Long id = actual.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.of(actual));
        //act
        Subject expected = service.getOneById(id);
        //assert
        assertFields(actual, expected);

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get a subject by id when entity not found")
    void testGetSubjectByIdWhenEntityNotFound() {
        //arrange
        Subject entity = factory.create();
        Long id = entity.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());
        //act
        //assert
        assertThrows(MyEntityNotFoundException.class, () -> service.getOneById(id));
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get all subjects when ok")
    void testGetAllSubjectsWhenOk() {
        //arrange
        List<Subject> entityList = factory.createList(5);
        Page<Subject> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(repository.findAll(pageable)).thenReturn(resultPage);
        //act
        Page<Subject> actual = service.getAll(pageable);
        //assert
        for (Subject subject : actual.getContent()) {
            Subject expected = entityList.get(entityList.indexOf(subject));
            assertFields(expected, subject);
        }
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Test count all subjects when ok")
    void testCountAllSubjectsWhenOk() {
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
        List<Subject> entityList = factory.createList(5);
        Page<Subject> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(specificationExecutor.findAll(any(Specification.class), eq(pageable))).thenReturn(resultPage);
        //act
        service.getBy("id=1;ORname=test;ANDdescription=test", pageable);
        //assert
        verify(specificationExecutor).findAll(any(Specification.class), eq(pageable));

    }

    void assertFields(Subject actual, Subject expected) {
        assertAll(
                () -> assertNotNull(expected),
                () -> assertEquals(actual.getId(), expected.getId()),
                () -> assertEquals(actual.getName(), expected.getName()),
                () -> assertEquals(actual.getDescription(), expected.getDescription()),
                () -> assertEquals(actual.getCreatedAt(), expected.getCreatedAt()),
                () -> assertEquals(actual.getUpdatedAt(), expected.getUpdatedAt())
        );
    }
}