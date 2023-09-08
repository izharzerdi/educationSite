package com.its.education.service;

import com.its.education.dto.CourseDTO;
import com.its.education.dto.CourseData;
import com.its.education.entity.Course;
import com.its.education.entity.StudentCourse;
import com.its.education.exception.CourseAlreadyExistsException;
import com.its.education.exception.CourseCannotDeleteException;
import com.its.education.exception.CourseNotFoundException;
import com.its.education.repository.CourseRepository;
import com.its.education.repository.StudentCourseRepository;
import com.its.education.validator.CourseValidator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseValidator courseValidator;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Override
    public CourseData getCourses() {
        var courses = courseRepository.findAll();
        var courseDTOS = CourseDTO.toDTO(courses);
        var courseData  = new CourseData();
        courseData.setCourses(courseDTOS);
        return courseData;
    }

    @Override
    public CourseDTO addCourse(CourseDTO courseDTO) {
        courseValidator.validateCourse(courseDTO);
        Course course = courseRepository.findByCode(courseDTO.getCode());
        if(Objects.nonNull(course)){
            throw new CourseAlreadyExistsException("Course already exists for code: "+courseDTO.getCode());
        }
        course = CourseDTO.toEntity(courseDTO);
        courseRepository.save(course);
        return new CourseDTO(course.getId());
    }

    @Override
    public CourseDTO updateCourse(Long courseId, CourseDTO courseDTO) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isEmpty()){
            throw new CourseNotFoundException("Course not found for given id: " + courseId);
        }
        courseValidator.validateCourse(courseDTO);
        Course course = courseOptional.get();
        course.setCode(courseDTO.getCode());
        course.setName(courseDTO.getName());
        var langCode = StringUtils.isNotBlank(courseDTO.getLangCode())? courseDTO.getLangCode(): "EN";
        var langName = StringUtils.isNotBlank(courseDTO.getLangName())? courseDTO.getLangName(): "English";
        course.setLangCode(langCode);
        course.setLangName(langName);
        courseRepository.save(course);
        return new CourseDTO(course.getId());
    }

    @Override
    public void deleteCourse(Long courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if(courseOptional.isEmpty()){
            throw new CourseNotFoundException("Course not found for given id: " + courseId);
        }
        List<StudentCourse> studentCourses = studentCourseRepository.findByCourseId(courseId);
        if(CollectionUtils.isNotEmpty(studentCourses)){
            throw new CourseCannotDeleteException("Course cannot be deleted as some students are associated"
                    + " to it");
        }
        courseRepository.delete(courseOptional.get());
    }
}
