package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.domain.dto.StudentTestDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StudentTestUnitTest {

    @Test
    void testDtoFieldAreRight() {
        //arrange
        StudentTest studentTest = new StudentTest();
        studentTest.setId(1L);
        studentTest.setStudent(new Student());
        studentTest.setAnswers("test");
        studentTest.setTest(new com.sleepypoem.testplatform.domain.entity.Test());
        studentTest.setScore(1);
        studentTest.setStatus(1);
        studentTest.setCreatedAt(LocalDateTime.now());
        studentTest.setUpdatedAt(LocalDateTime.now());
        //act
        StudentTestDto studentTestDto = studentTest.toDto();
        //assert
        assertAll(
                () -> assertEquals(studentTest.getId(), studentTestDto.getId()),
                () -> assertNotNull(studentTestDto.getStudent()),
                () -> assertEquals(studentTest.getAnswers(), studentTestDto.getAnswers()),
                () -> assertNotNull(studentTestDto.getTest()),
                () -> assertEquals(studentTest.getScore(), studentTestDto.getScore()),
                () -> assertEquals(studentTest.getStatus(), studentTestDto.getStatus()),
                () -> assertEquals(studentTest.getCreatedAt(), studentTestDto.getCreatedAt()),
                () -> assertEquals(studentTest.getUpdatedAt(), studentTestDto.getUpdatedAt())
        );
    }

}