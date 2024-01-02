package com.sleepypoem.testplatform.domain.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sleepypoem.testplatform.domain.dto.ImageQuestion;
import com.sleepypoem.testplatform.domain.dto.MultiChoiceQuestion;
import com.sleepypoem.testplatform.domain.dto.OpenChoiceQuestion;
import com.sleepypoem.testplatform.domain.dto.Question;
import com.sleepypoem.testplatform.exception.MyInternalException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionParser {

    private final ObjectMapper objectMapper;

    public QuestionParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Question> parse(String content) {
        List<Question> questions = new ArrayList<>();
        try {
            JsonNode jsonNode = objectMapper.readTree(content);
            for (int i = 0; i < jsonNode.size(); i++) {
                String type = jsonNode.get(i).get("type").asText().toLowerCase();
                switch (type) {
                    case "multiple": questions.add(objectMapper.readValue(jsonNode.get(i).toString(), MultiChoiceQuestion.class)); break;
                    case "open": questions.add(objectMapper.readValue(jsonNode.get(i).toString(), OpenChoiceQuestion.class)); break;
                    case "image": questions.add(objectMapper.readValue(jsonNode.get(i).toString(), ImageQuestion.class)); break;
                    default: throw new MyInternalException("Unknown type: " + type + " in question: " + jsonNode.get(i).toString() + " at index: " + i + " in content: " + content);
                }
            }
        }catch (Exception e) {
            throw new MyInternalException(e.getMessage());
        }
        return questions;
    }
}
