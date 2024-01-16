package com.sleepypoem.testplatform.controller;

import com.sleepypoem.testplatform.domain.dto.PaginatedDto;
import com.sleepypoem.testplatform.domain.dto.RoleDto;
import com.sleepypoem.testplatform.domain.entity.Role;
import com.sleepypoem.testplatform.service.AbstractQueryableService;
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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    RoleController controller;

    @Mock
    AbstractQueryableService<Long, Role> service;

    RoleFactory factory;

    @BeforeEach
    void setUp() {
        controller = new RoleController(service);
        factory = new RoleFactory();
    }

    @Test
    @DisplayName("Test get all roles")
    void getAllRoles() {
        //arrange
        Page<Role> roles = new PageImpl<>(factory.createList(5));
        when(service.getAll(any(Pageable.class))).thenReturn(roles);
        //act
        ResponseEntity<PaginatedDto<RoleDto>> response = controller.all(PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Test get role by id")
    void getRoleById() {
        //arrange
        Role role = factory.create();
        when(service.getOneById(any(Long.class))).thenReturn(role);
        //act
        ResponseEntity<RoleDto> response = controller.one(1L);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getOneById(any(Long.class));
    }

    @Test
    @DisplayName("Test query roles")
    void testQueryRolesWhenOk() {
        //arrange
        Page<Role> roles = new PageImpl<>(factory.createList(5));
        when(service.getBy(any(String.class), any(Pageable.class))).thenReturn(roles);
        //act
        ResponseEntity<PaginatedDto<RoleDto>> response = controller.search("test", PageRequest.of(0, 5));
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).getBy(any(String.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Test create a role")
    void createRole() {
        //arrange
        Role role = factory.create();
        when(service.create(any(Role.class))).thenReturn(role);
        //act
        ResponseEntity<RoleDto> response = controller.create(role);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).create(role);
    }

    @Test
    @DisplayName("Test update a role")
    void updateRole() {
        //arrange
        Role role = factory.create();
        when(service.update(any(Long.class), any(Role.class))).thenReturn(role);
        //act
        ResponseEntity<RoleDto> response = controller.update(1L, role);
        //assert
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertNotNull(response.getBody());
        verify(service).update(any(Long.class), any(Role.class));
    }


    @Test
    @DisplayName("Test delete a role")
    void deleteRole() {
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
    @DisplayName("Test delete a role when error")
    void deleteRoleWhenError() {
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