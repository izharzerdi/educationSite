package com.its.education.repository;

import com.its.education.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCode(String code);

    List<Course> findByIdIn(Set<Long> courseIds);
}
