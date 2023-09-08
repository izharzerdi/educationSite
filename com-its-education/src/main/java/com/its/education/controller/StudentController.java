package com.its.education.controller;

import com.its.education.common.APIResponse;
import com.its.education.common.CreateResponseDTO;
import com.its.education.dto.*;
import com.its.education.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<APIResponse<StudentData>> fetchAllStudents(){
        StudentData studentData = studentService.findAllStudents();
        var response = APIResponse.data(studentData);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/students")
    public ResponseEntity<APIResponse<CreateResponseDTO>> addStudents(@RequestBody StudentDTO studentDTO){
        var student = studentService.addStudent(studentDTO);
        var createResponseDTO = new CreateResponseDTO(student.getId().toString(),"Student registered successFully");
        var response = APIResponse.data(createResponseDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/students/courses")
    public ResponseEntity<APIResponse<CourseData>> fetchAllCoursesAvailable(){
        CourseData courseData = studentService.findAllCoursesAvailable();
        var response = APIResponse.data(courseData);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/students/{id}/courses")
    public ResponseEntity<APIResponse<StudentCourseData>> fetchAllStudentCourses(@PathVariable("id") Long studentId){
        StudentCourseData studentCourseData = studentService.findAllStudentCourses(studentId);
        var response = APIResponse.data(studentCourseData);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/students/{id}/courses")
    public ResponseEntity<APIResponse<CreateResponseDTO>> addCoursesToStudent(@PathVariable("id") Long studentId,
            @RequestBody StudentCourseData studentCourse){
        StudentCourseData studentCourseData = studentService.addCoursesToStudent(studentId, studentCourse);
        var createResponseDTO = new CreateResponseDTO(studentCourseData.getStudentId().toString(),
                "Courses Added Successfully");
        var response = APIResponse.data(createResponseDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<APIResponse<CreateResponseDTO>> deleteStudentAndCorrespondingCourses(@PathVariable("id") Long studentId){
        studentService.deleteStudent(studentId);
        var createResponseDTO = new CreateResponseDTO(HttpStatus.OK.toString(), "Student deleted SuccessFully");
        var response = APIResponse.data(createResponseDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
