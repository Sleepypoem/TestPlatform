package com.sleepypoem.testplatform.domain.entity;

import com.sleepypoem.testplatform.domain.dto.SubjectDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SubjectTest {

    @Test
    void testDtoFieldsAreRight() {
        //arrange
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("name");
        subject.setDescription("test");
        subject.setCreatedAt(LocalDateTime.now());
        subject.setUpdatedAt(LocalDateTime.now());
        //act
        SubjectDto actual = subject.toDto();
        //assert
        assertAll(
                () -> assertEquals(subject.getId(), actual.getId()),
                () -> assertEquals(subject.getName(), actual.getName()),
                () -> assertEquals(subject.getDescription(), actual.getDescription()),
                () -> assertEquals(subject.getCreatedAt(), actual.getCreatedAt()),
                () -> assertEquals(subject.getUpdatedAt(), actual.getUpdatedAt())
        );
    }


}