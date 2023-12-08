package com.sleepypoem.testplatform.domain.dto;

import com.sleepypoem.testplatform.domain.entity.Image;
import lombok.Data;

@Data
public class ImageQuestion extends Question {

    private Image[] images;
}
