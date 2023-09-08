package com.its.education.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentCourseData {
    private Long studentId;
    private List<CourseDTO> courses;
}
