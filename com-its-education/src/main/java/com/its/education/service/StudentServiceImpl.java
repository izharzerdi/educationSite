package com.its.education.service;

import com.its.education.dto.CourseDTO;
import com.its.education.dto.CourseData;
import com.its.education.dto.StudentCourseData;
import com.its.education.dto.StudentDTO;
import com.its.education.dto.StudentData;
import com.its.education.entity.Course;
import com.its.education.entity.Student;
import com.its.education.entity.StudentCourse;
import com.its.education.exception.*;
import com.its.education.repository.CourseRepository;
import com.its.education.repository.StudentCourseRepository;
import com.its.education.repository.StudentRepository;
import com.its.education.validator.StudentValidator;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentValidator studentValidator;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Override
    public StudentData findAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOS = StudentDTO.toDTO(students);
        StudentData studentData = new StudentData();
        studentData.setStudents(studentDTOS);
        return studentData;
    }

    @Override
    public CourseData findAllCoursesAvailable() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS = CourseDTO.toDTO(courses);
        CourseData courseData = new CourseData();
        courseData.setCourses(courseDTOS);
        return courseData;
    }

    @Override
    public StudentDTO addStudent(StudentDTO studentDTO) {
        studentValidator.validateStudent(studentDTO);
        var student = studentRepository.findByEmailAddress(studentDTO.getEmailAddress());
        if(Objects.nonNull(student)){
            throw new StudentAlreadyExistsException("EmailId already exists");
        }
        student = StudentDTO.toEntity(studentDTO);
        studentRepository.save(student);
        return new StudentDTO(student.getId());
    }

    @Override
    public StudentCourseData findAllStudentCourses(Long studentId) {
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudentId(studentId);
        List<Course> courses = studentCourses.stream().map(StudentCourse::getCourse)
                .collect(Collectors.toList());
        StudentCourseData studentCourseData = new StudentCourseData();
        List<CourseDTO> courseDTOS = CourseDTO.toDTO(courses);
        studentCourseData.setStudentId(studentId);
        studentCourseData.setCourses(courseDTOS);
        return studentCourseData;
    }

    @Override
    public StudentCourseData addCoursesToStudent(Long studentId, StudentCourseData studentCourseData) {
        studentValidator.validateCourses(studentCourseData.getCourses());
        Set<Long> courseIds = studentCourseData.getCourses().stream().map(CourseDTO::getId).collect(Collectors.toSet());
        List<Course> courses = courseRepository.findByIdIn(courseIds);
        Map<Long, Course> courseMap = courses.stream().collect(Collectors.toMap(Course::getId, Function.identity()));
        checkCoursesArePresent(courseIds, courseMap);
        addStudentCourse(studentId, courses, courseIds);
        StudentCourseData studentCourseDataRes = new StudentCourseData();
        studentCourseDataRes.setStudentId(studentId);
        return studentCourseDataRes ;
    }

    @Override
    public void deleteStudent(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(optionalStudent.isEmpty()){
            throw new StudentNotFoundException("Student not found for given id: "+studentId);
        }
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudentId(studentId);
        if(CollectionUtils.isNotEmpty(studentCourses)){
            studentCourseRepository.deleteAll(studentCourses);
        }
        studentRepository.deleteById(studentId);
    }

    private void addStudentCourse(Long studentId, List<Course> courses, Set<Long> courseIds) {
        var studentCourses = new ArrayList<StudentCourse>();
        var studentCourseList = studentCourseRepository.findByStudentIdAndCourseIdIn(studentId,
                courseIds);
        checkCourseAlreadyPresent(studentCourseList,courseIds);
        courses.forEach(course -> {
            var studentCourse = new StudentCourse();
            studentCourse.setStudentId(studentId);
            studentCourse.setCourse(course);
            studentCourses.add(studentCourse);
        });
        studentCourseRepository.saveAll(studentCourses);
    }

    private void checkCourseAlreadyPresent(List<StudentCourse> studentCourseList, Set<Long> courseIds) {
        courseIds.forEach(courseId -> studentCourseList.forEach(studentCourse -> {
            if(courseId.equals(studentCourse.getCourse().getId())){
                throw new StudentRequestException("Course with id: " + courseId + " is already present for the student");
            }
        }));
    }

    private void checkCoursesArePresent(Set<Long> courseIds, Map<Long, Course> courseMap) {
        courseIds.forEach(courseId -> {
            if(!courseMap.containsKey(courseId)){
                throw new CourseNotFoundException("No Course found for given id: "+courseId);
            }
        });
    }
}
