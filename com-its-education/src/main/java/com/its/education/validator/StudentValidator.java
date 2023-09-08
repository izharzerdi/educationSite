package com.its.education.validator;

import com.its.education.dto.CourseDTO;
import com.its.education.dto.StudentDTO;
import com.its.education.exception.CourseRequestException;
import com.its.education.exception.StudentRequestException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class StudentValidator {
    private static final String NAME_PATTERN = "^[\\p{L}\\s'-]+$";
    private static final String UNICODE_EMAIL_PATTERN = "^[\\p{L}\\p{N}\\._%+-]+@[\\p{L}\\p{N}\\.\\-]+\\.[\\p{L}]{2,}$";
    private static final String PHONE_NUMBER_PATTERN = "^(\\+?\\d{1,4}[-.\\s]?)?(\\(?\\d{3}\\)?[-.\\s]?)?\\d{3}[-.\\s]?\\d{4}$";

    public void validateStudent(StudentDTO studentDTO) {
        if (StringUtils.isBlank(studentDTO.getFullName())) {
            throw new StudentRequestException("Name is mandatory");
        }
        if (StringUtils.isBlank(studentDTO.getEmailAddress())) {
            throw new CourseRequestException("Email is mandatory");
        }

        if (StringUtils.isBlank(studentDTO.getTelephoneNumber())) {
            throw new CourseRequestException("Telephone Number is mandatory");
        }

        if (StringUtils.isBlank(studentDTO.getAddress())) {
            throw new CourseRequestException("Address is mandatory");
        }
        validateStudentData(studentDTO);
    }

    public void validateStudentData(StudentDTO studentDTO){
        if(!studentDTO.getFullName().matches(NAME_PATTERN)){
            throw new StudentRequestException("Please enter valid name");
        }
        validateLength(studentDTO.getFullName());
        if (!EmailValidator.getInstance().isValid(studentDTO.getEmailAddress())) {
            if (!Pattern.matches(UNICODE_EMAIL_PATTERN, studentDTO.getEmailAddress())) {
                throw new StudentRequestException("Please enter valid email address");
            }
        }
        validateLength(studentDTO.getEmailAddress());
        validateLength(studentDTO.getAddress());
        if(!studentDTO.getTelephoneNumber().matches(PHONE_NUMBER_PATTERN)){
            throw new StudentRequestException("Please enter valid phone number");
        }
        validateLength(studentDTO.getTelephoneNumber());
    }

    private void validateLength(String fieldValue) {
        if(StringUtils.isNotBlank(fieldValue) && fieldValue.length() > 255){
            throw new StudentRequestException("Please enter valid name");
        }
    }

    public void validateCourses(List<CourseDTO> courseDTOList) {
        if(CollectionUtils.isEmpty(courseDTOList)){
            throw new CourseRequestException("Please provide courses info");
        }
        courseDTOList.forEach(courseDTO -> {
            if(Objects.isNull(courseDTO.getId())){
                throw new CourseRequestException("Please provide course id");
            }
        });
    }
}
