package com.sleepypoem.testplatform.service;

import com.sleepypoem.testplatform.domain.entity.Image;
import com.sleepypoem.testplatform.domain.entity.Role;
import com.sleepypoem.testplatform.exception.MyEntityNotFoundException;
import com.sleepypoem.testplatform.exception.MyValidationException;
import com.sleepypoem.testplatform.service.validation.DefaultValidator;
import com.sleepypoem.testplatform.service.validation.RoleValidator;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
import com.sleepypoem.testplatform.testutils.factories.impl.RoleFactory;
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
class RoleServiceTest {

    @Mock
    private JpaRepository<Role, Long> repository;

    @Mock
    private JpaSpecificationExecutor<Role> specificationExecutor;

    private RoleService service;

    private SimpleFactory<Role> factory;

    @BeforeEach
    void setUp() {
        DefaultValidator<Role> validator = new DefaultValidator<>();
        service = new RoleService(repository, specificationExecutor, validator);
        factory = new RoleFactory();
    }

    @Test
    @DisplayName("Test create a role when ok")
    void testCreateRoleWhenOk() {
        //arrange
        Role expected = factory.create();
        when(repository.save(expected)).thenReturn(expected);
        //act
        Role actual = service.create(expected);
        //assert
        assertFields(expected, actual);

        verify(repository).save(expected);
    }

    @Test
    @DisplayName("Test create an Role when role is null")
    void testCreateRoleWhenRoleIsNull() {
        assertThrows(NullPointerException.class, () -> service.create(null));
    }

    @Test
    @DisplayName("Test update a role when ok")
    void testUpdateRoleWhenOk() {
        //arrange
        Role expected = factory.create();
        Long id = expected.getId();
        when(repository.saveAndFlush(expected)).thenReturn(expected);
        //act
        Role actual = service.update(id, expected);
        //assert
        assertFields(expected, actual);

        verify(repository).saveAndFlush(expected);
    }

    @Test
    @DisplayName("Test update a role when role is null")
    void testUpdateRoleWhenRoleIsNull() {
        assertThrows(NullPointerException.class, () -> service.update(1L, null));
    }

    @Test
    @DisplayName("Test update a role when id and roleId are not the same")
    void testUpdateRoleWhenIdAndRoleIdAreNotTheSame() {
        Role entity = factory.create();
        entity.setId(3L);
        assertThrows(IllegalArgumentException.class, () -> service.update(2L, entity));

    }

    @Test
    @DisplayName("Test delete a role by id when ok")
    void testDeleteRoleByIdWhenOk() {
        //arrange
        Role entity = factory.create();
        Long id = entity.getId();
        //act
        service.delete(id);
        //assert
        verify(repository).deleteById(id);
    }

    @Test
    @DisplayName("Test delete a role by id when id is null")
    void testDeleteRoleWhenOk() {
        //arrange
        Role entity = factory.create();
        //act
        service.delete(entity);
        //assert
        verify(repository).delete(entity);
    }

    @Test
    @DisplayName("Test get a role by id when ok")
    void testGetRoleByIdWhenOk() {
        //arrange
        Role actual = factory.create();
        Long id = actual.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.of(actual));
        //act
        Role expected = service.getOneById(id);
        //assert
        assertFields(actual, expected);

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get a role by id when entity not found")
    void testGetRoleByIdWhenEntityNotFound() {
        //arrange
        Role entity = factory.create();
        Long id = entity.getId();
        when(repository.findById(id)).thenReturn(java.util.Optional.empty());
        //act
        //assert
        assertThrows(MyEntityNotFoundException.class, () -> service.getOneById(id));
        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Test get all roles when ok")
    void testGetAllRolesWhenOk() {
        //arrange
        List<Role> entityList = factory.createList(5);
        Page<Role> resultPage = new PageImpl<>(entityList);
        Pageable pageable = PageRequest.of(0, 5);
        when(repository.findAll(pageable)).thenReturn(resultPage);
        //act
        Page<Role> actual = service.getAll(pageable);
        //assert
        for (Role role : actual.getContent()) {
            Role expected = entityList.get(entityList.indexOf(role));
            assertFields(expected, role);
        }
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Test count all roles when ok")
    void testCountAllRolesWhenOk() {
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
        Role entity = factory.create();
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
        Role entity = factory.create();
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
        List<Role> entityList = factory.createList(5);
        Page<Role> resultPage = new PageImpl<>(entityList);
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
        Role role = factory.create();
        RoleValidator validatorMock = mock(RoleValidator.class);
        service.setValidator(validatorMock);
        when(validatorMock.isValid(any(Role.class))).thenReturn(Map.of("test", "test"));
        var ex = assertThrows(MyValidationException.class, () -> service.create(role));
        String expectedMessage = "The following errors were found during validation : {\n" +
                "Field: test || Error: test\n" +
                "}";
        assertEquals(expectedMessage, ex.getMessage());
        verify(validatorMock).isValid(any(Role.class));
    }

    @Test
    @DisplayName("Test exception is thrown when validation fails in update method")
    void testValidationFailsInUpdateMethod() {
        Role role = factory.create();
        Long id = role.getId();
        RoleValidator validatorMock = mock(RoleValidator.class);
        service.setValidator(validatorMock);
        when(validatorMock.isValid(any(Role.class))).thenReturn(Map.of("test", "test"));
        var ex = assertThrows(MyValidationException.class, () -> service.update(id, role));
        String expectedMessage = """
                The following errors were found during validation : {
                Field: test || Error: test
                }""";
        assertEquals(expectedMessage, ex.getMessage());
        verify(validatorMock).isValid(any(Role.class));
    }

        void assertFields(Role actual, Role expected) {
        assertAll(
                () -> assertNotNull(expected),
                () -> assertEquals(actual.getId(), expected.getId()),
                () -> assertEquals(actual.getName(), expected.getName()),
                () -> assertEquals(actual.getDescription(), expected.getDescription()),
                () -> assertNotNull(actual.getTeachers()),
                () -> assertEquals(actual.getCreatedAt(), expected.getCreatedAt()),
                () -> assertEquals(actual.getUpdatedAt(), expected.getUpdatedAt())
        );
    }
}