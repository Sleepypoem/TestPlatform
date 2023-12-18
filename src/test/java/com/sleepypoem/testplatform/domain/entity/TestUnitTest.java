package com.sleepypoem.testplatform.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.sleepypoem.testplatform.domain.dto.TestDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class TestUnitTest {
    @Test
    void testDtoFieldsAreRight() {
        //arrange
        com.sleepypoem.testplatform.domain.entity.Test test = new com.sleepypoem.testplatform.domain.entity.Test();
        test.setId(1L);
        test.setName("name");
        test.setContent("test");
        test.setSubject(new Subject());
        test.setTeacher(new Teacher());
        test.setCreatedAt(LocalDateTime.now());
        test.setUpdatedAt(LocalDateTime.now());
        //act
        TestDto actual = test.toDto();
        //assert
        assertAll(
                () -> assertEquals(test.getId(), actual.getId()),
                () -> assertEquals(test.getName(), actual.getName()),
                () -> assertEquals(test.getContent(), actual.getContent()),
                () -> assertNotNull(actual.getSubject()),
                () -> assertNotNull(actual.getTeacher()),
                () -> assertEquals(test.getCreatedAt(), actual.getCreatedAt()),
                () -> assertEquals(test.getUpdatedAt(), actual.getUpdatedAt())
        );
    }


}