package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.domain.dto.TeacherDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static com.sleepypoem.testplatform.constants.ResourceConstants.TEACHER_ROLES_PREFIX;
import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    @Test
    void testDtoFieldAreRight() {
        //arrange
        String rolesString = TEACHER_ROLES_PREFIX + 1L;
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("test");
        teacher.setLastName("test");
        teacher.setTeacherCode(0134567L);
        teacher.setRoles(Set.of(new Role()));
        teacher.setImage(new Image());
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());
        //act
        TeacherDto teacherDto = teacher.toDto();
        //assert
        System.out.println(teacherDto.toString());
        assertAll(
                () -> assertEquals(teacher.getId(), teacherDto.getId()),
                () -> assertEquals(teacher.getFirstName(), teacherDto.getFirstName()),
                () -> assertEquals(teacher.getLastName(), teacherDto.getLastName()),
                () -> assertEquals(teacher.getTeacherCode(), teacherDto.getTeacherCode()),
                () -> assertEquals(rolesString, teacherDto.getRoles()),
                () -> assertNotNull(teacherDto.getImage()),
                () -> assertEquals(teacher.getCreatedAt(), teacherDto.getCreatedAt()),
                () -> assertEquals(teacher.getUpdatedAt(), teacherDto.getUpdatedAt())
        );
    }

}