package com.sleepypoem.testplatform.domain.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sleepypoem.testplatform.domain.dto.ImageQuestion;
import com.sleepypoem.testplatform.domain.dto.MultiChoiceQuestion;
import com.sleepypoem.testplatform.domain.dto.OpenChoiceQuestion;
import com.sleepypoem.testplatform.domain.dto.Question;
import com.sleepypoem.testplatform.exception.MyInternalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionParserTest {

    QuestionParser questionParser;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        questionParser = new QuestionParser(objectMapper);
    }

    @Test
    @DisplayName("Test parse a string into a list of questions")
    void testIfParseIsDoneCorrectly(){
        String string = "[" +
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

        List<Question> questionList = questionParser.parse(string);
        assertNotNull(questionList);
        assertEquals(3, questionList.size());
        assertInstanceOf(OpenChoiceQuestion.class, questionList.get(0));
        assertInstanceOf(MultiChoiceQuestion.class, questionList.get(1));
        assertInstanceOf(ImageQuestion.class, questionList.get(2));
    }

    @Test
    @DisplayName("Test if an error is present when type is unknown")
    void testIfErrorIsPresentWhenTypeIsUnknown() {
        String string = "[{\"type\": \"UNKNOWN\"," +
                " \"answer\": \"answer\"," +
                " \"description\" : \"description\"}]";
        assertThrows(MyInternalException.class, () -> questionParser.parse(string));

    }
}