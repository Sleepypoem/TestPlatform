package com.sleepypoem.testplatform.testutils.factories.impl;

import com.sleepypoem.testplatform.domain.dto.Question;
import com.sleepypoem.testplatform.domain.entity.Subject;
import com.sleepypoem.testplatform.domain.entity.Teacher;
import com.sleepypoem.testplatform.domain.entity.Test;
import com.sleepypoem.testplatform.testutils.factories.abstracts.SimpleFactory;
import com.sleepypoem.testplatform.testutils.random.RandomGenerator;

import java.time.LocalDateTime;

public class TestFactory implements SimpleFactory<Test> {

    private static final String content = "[" +
            "{\"type\": \"OPEN\"," +
            " \"answer\": \"answer\"," +
            " \"description\" : \"description\"}," +
            "{\"type\": \"MULTIPLE\"," +
            " \"answer\": \"answer\"," +
            " \"description\" : \"description\"," +
            " \"options\" : [\"option1\", \"option2\"]}," +
            "{\"type\": \"IMAGE\"," +
            " \"answer\": \"answer\"," +
            " \"description\" : \"description\"," +
            " \"images\" : [" +
            "{\"id\": 1," +
            "\"name\": \"name\","+
            "\"format\":\"JPG\"," +
            "\"path\":\"www.image.com\"," +
            "\"size\": 200," +
            "\"width\":400," +
            "\"height\":300" +
            "}]}]";
    @Override
    public Test create() {
        Test test = new Test();
        test.setId(RandomGenerator.getRandomLong(1, 999));
        test.setName(RandomGenerator.getRandomString(10));
        test.setContent(content);
        test.setSubject(new Subject());
        test.setTeacher(new Teacher());
        test.setCreatedAt(LocalDateTime.now());
        test.setUpdatedAt(LocalDateTime.now());
        return test;
    }

}
