package com.its.education.controller;

import com.its.education.common.APIResponse;
import com.its.education.common.CreateResponseDTO;
import com.its.education.dto.CourseDTO;
import com.its.education.dto.CourseData;
import com.its.education.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<APIResponse<CourseData>> fetchAllCourses(){
        CourseData courseData = courseService.getCourses();
        var response = APIResponse.data(courseData);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/courses")
    public ResponseEntity<APIResponse<CreateResponseDTO>> addCourse(@RequestBody CourseDTO courseDTO){
        CourseDTO course = courseService.addCourse(courseDTO);
        var createResponseDTO = new CreateResponseDTO(course.getId().toString(),"Course SuccessFully created");
        var response = APIResponse.data(createResponseDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PatchMapping("/courses/{id}")
    public ResponseEntity<APIResponse<CreateResponseDTO>> updateCourse(@RequestBody CourseDTO courseDTO,
            @PathVariable("id") Long courseId){
        CourseDTO course = courseService.updateCourse(courseId, courseDTO);
        var createResponseDTO = new CreateResponseDTO(course.getId().toString(),"Course Updated SuccessFully");
        var response = APIResponse.data(createResponseDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<APIResponse<CreateResponseDTO>> deleteCourse(@PathVariable("id") Long courseId){
         courseService.deleteCourse(courseId);
        var createResponseDTO = new CreateResponseDTO(HttpStatus.OK.toString(), "Course deleted SuccessFully");
        var response = APIResponse.data(createResponseDTO);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
