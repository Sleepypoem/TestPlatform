package com.sleepypoem.testplatform.config;

import com.sleepypoem.testplatform.domain.dto.TeacherDto;
import com.sleepypoem.testplatform.domain.entity.Teacher;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import static com.sleepypoem.testplatform.constants.ResourceConstants.TEACHER_ROLES_PREFIX;

public class TeacherConverter implements Converter<Teacher, TeacherDto> {
    @Override
    public TeacherDto convert(MappingContext<Teacher, TeacherDto> context) {
        if (context.getSource() == null) {
            return null;
        }
        Teacher s = context.getSource();
        TeacherDto d = new TeacherDto();
        d.setId(s.getId());
        d.setFirstName(s.getFirstName());
        d.setLastName(s.getLastName());
        d.setTeacherCode(s.getTeacherCode());
        d.setRoles(TEACHER_ROLES_PREFIX + s.getId());
        if(s.getImage() == null) {
            d.setImage(null);
        }else{
            d.setImage(s.getImage().toDto());
        }
        d.setCreatedAt(s.getCreatedAt());
        d.setUpdatedAt(s.getUpdatedAt());
        return d;
    }
}
