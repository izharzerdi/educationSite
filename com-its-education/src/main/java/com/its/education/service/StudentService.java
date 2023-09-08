package com.its.education.service;

import com.its.education.dto.CourseData;
import com.its.education.dto.StudentCourseData;
import com.its.education.dto.StudentDTO;
import com.its.education.dto.StudentData;

public interface StudentService {
    StudentData findAllStudents();

    CourseData findAllCoursesAvailable();

    StudentDTO addStudent(StudentDTO studentDTO);

    StudentCourseData findAllStudentCourses(Long studentId);

    StudentCourseData addCoursesToStudent(Long studentId, StudentCourseData courseDTOList);

    void deleteStudent(Long studentId);
}
