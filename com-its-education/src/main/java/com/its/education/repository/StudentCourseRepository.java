package com.its.education.repository;

import com.its.education.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    List<StudentCourse> findByStudentId(Long studentId);

    List<StudentCourse> findByCourseId(Long courseId);

    List<StudentCourse> findByStudentIdAndCourseIdIn(Long studentId, Set<Long> courseIds);
}
