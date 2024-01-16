package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.domain.dto.RoleDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {

    @Test
    void testDtoFieldAreRight() {
        //arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("admin");
        role.setDescription("administrator");
        role.setTeachers(List.of(new Teacher()));
        //act
        RoleDto actual = role.toDto();
        //assert
        assertAll(
                () -> assertEquals(1L, actual.getId()),
                () -> assertEquals("admin", actual.getName()),
                () -> assertEquals("administrator", actual.getDescription())
        );

    }

}