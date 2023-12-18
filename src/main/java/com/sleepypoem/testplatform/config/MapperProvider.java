package com.sleepypoem.testplatform.config;

import com.sleepypoem.testplatform.domain.dto.TeacherDto;
import com.sleepypoem.testplatform.domain.entity.Teacher;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

public class MapperProvider {

    private static ModelMapper instance;

    private MapperProvider() {
    }

    public static ModelMapper getMapper() {

        if (instance == null) {
            Converter<Teacher, TeacherDto> teacherConverter = new TeacherConverter();
            instance = new ModelMapper();
            instance.addConverter(teacherConverter);
        }
        return instance;
    }

}
