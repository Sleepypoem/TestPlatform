package com.sleepypoem.testplatform.domain.dto;

import lombok.Data;

@Data
public class OpenChoiceQuestion extends Question {

    private String[] responses;
}
