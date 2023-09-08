package com.its.education.dto;

import com.its.education.entity.Course;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private String code;
    private String name;
    private String langCode;
    private String langName;

    public CourseDTO(Long id) {
        this.id = id;
    }

    public static List<CourseDTO> toDTO(List<Course> courses){
        List<CourseDTO> courseDTOs = new ArrayList<>();
        courses.forEach(course -> {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(course.getId());
            courseDTO.setCode(course.getCode());
            courseDTO.setName(course.getName());
            courseDTO.setLangCode(course.getLangCode());
            courseDTO.setLangName(course.getLangName());
            courseDTOs.add(courseDTO);
        });
        return courseDTOs;
    }

    public static Course toEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setCode(courseDTO.getCode());
        var langCode = StringUtils.isNotBlank(courseDTO.getLangCode())? courseDTO.getLangCode(): "EN";
        var langName = StringUtils.isNotBlank(courseDTO.getLangName())? courseDTO.getLangName(): "English";
        course.setLangName(langName);
        course.setLangCode(langCode);
        return course;
    }
}
