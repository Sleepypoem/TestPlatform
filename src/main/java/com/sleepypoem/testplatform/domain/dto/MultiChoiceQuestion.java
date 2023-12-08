package com.sleepypoem.testplatform.domain.dto;

import lombok.Data;

@Data
public class MultiChoiceQuestion extends Question {

    private String[] options;
}
