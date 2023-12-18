package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.domain.dto.StudentDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StudentUnitTest {

    @Test
    void testDtoFieldAreRight() {
        //arrange
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Test");
        student.setLastName("Test");
        student.setStudentCode(12345678789L);
        student.setGradeLevel(3);
        student.setImage(new Image());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        //act
        StudentDto studentDto = student.toDto();
        //assert
        assertAll(
                () -> assertEquals(student.getId(), studentDto.getId()),
                () -> assertEquals(student.getFirstName(), studentDto.getFirstName()),
                () -> assertEquals(student.getLastName(), studentDto.getLastName()),
                () -> assertEquals(student.getStudentCode(), studentDto.getStudentCode()),
                () -> assertEquals(student.getGradeLevel(), studentDto.getGradeLevel()),
                () -> assertNotNull(studentDto.getImage()),
                () -> assertEquals(student.getCreatedAt(), studentDto.getCreatedAt()),
                () -> assertEquals(student.getUpdatedAt(), studentDto.getUpdatedAt())
        );
    }

}