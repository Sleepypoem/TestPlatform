package com.sleepypoem.testplatform.domain.dto;

import com.sleepypoem.testplatform.enums.QuestionType;
import lombok.Data;

@Data
public class Question {

    protected QuestionType type;
    protected String description;
    protected String answer;
}
