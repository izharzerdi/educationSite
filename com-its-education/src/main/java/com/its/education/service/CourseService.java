package com.its.education.service;

import com.its.education.dto.CourseDTO;
import com.its.education.dto.CourseData;

public interface CourseService {
    CourseData getCourses();

    CourseDTO addCourse(CourseDTO courseDTO);

    CourseDTO updateCourse(Long courseId, CourseDTO courseDTO);

    void deleteCourse(Long courseId);
}
