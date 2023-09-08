package com.its.education.validator;

import com.its.education.dto.CourseDTO;
import com.its.education.exception.CourseRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CourseValidator {

    public void validateCourse(CourseDTO courseDTO) {
        if (StringUtils.isBlank(courseDTO.getCode())) {
            throw new CourseRequestException("Course code is mandatory");
        }
        if (StringUtils.isBlank(courseDTO.getName())) {
            throw new CourseRequestException("Course name is mandatory");
        }
    }
}
