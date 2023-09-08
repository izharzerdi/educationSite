package com.its.education.exception;

import com.its.education.common.APIResponse;
import com.its.education.common.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<APIResponse<String>> genericException(Exception ex, HttpServletRequest request){
        Error error= new Error(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getLocalizedMessage());
        APIResponse<String> data = APIResponse.statusAndError(HttpStatus.INTERNAL_SERVER_ERROR.value(), error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(data);
    }

    @ExceptionHandler({ CourseAlreadyExistsException.class})
    public ResponseEntity<APIResponse<String>> handleCourseAlreadyExistsException(CourseAlreadyExistsException exception) {
        Error error= new Error(HttpStatus.BAD_REQUEST.toString(), exception.getLocalizedMessage());
        APIResponse<String> data = APIResponse.statusAndError(HttpStatus.BAD_REQUEST.value(), error);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(data);
    }

    @ExceptionHandler({ CourseRequestException.class})
    public ResponseEntity<APIResponse<String>> handleCourseRequestException(CourseRequestException exception) {
        Error error= new Error(HttpStatus.BAD_REQUEST.toString(), exception.getLocalizedMessage());
        APIResponse<String> data = APIResponse.statusAndError(HttpStatus.BAD_REQUEST.value(), error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }

    @ExceptionHandler({ CourseNotFoundException.class})
    public ResponseEntity<APIResponse<String>> handleCourseNotFoundException(CourseNotFoundException exception) {
        Error error= new Error(HttpStatus.NOT_FOUND.toString(), exception.getLocalizedMessage());
        APIResponse<String> data = APIResponse.statusAndError(HttpStatus.NOT_FOUND.value(), error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler({ StudentRequestException.class})
    public ResponseEntity<APIResponse<String>> handleStudentRequestException(StudentRequestException exception) {
        Error error= new Error(HttpStatus.BAD_REQUEST.toString(), exception.getLocalizedMessage());
        APIResponse<String> data = APIResponse.statusAndError(HttpStatus.BAD_REQUEST.value(), error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }

    @ExceptionHandler({ StudentAlreadyExistsException.class})
    public ResponseEntity<APIResponse<String>> handleStudentAlreadyExistsException(StudentAlreadyExistsException exception) {
        Error error= new Error(HttpStatus.BAD_REQUEST.toString(), exception.getLocalizedMessage());
        APIResponse<String> data = APIResponse.statusAndError(HttpStatus.BAD_REQUEST.value(), error);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(data);
    }

    @ExceptionHandler({ StudentNotFoundException.class})
    public ResponseEntity<APIResponse<String>> handleStudentNotFoundException(StudentNotFoundException exception) {
        Error error= new Error(HttpStatus.NOT_FOUND.toString(), exception.getLocalizedMessage());
        APIResponse<String> data = APIResponse.statusAndError(HttpStatus.NOT_FOUND.value(), error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler({ CourseCannotDeleteException.class})
    public ResponseEntity<APIResponse<String>> handleCourseCannotDeleteException(CourseCannotDeleteException exception) {
        Error error= new Error(HttpStatus.UNPROCESSABLE_ENTITY.toString(), exception.getLocalizedMessage());
        APIResponse<String> data = APIResponse.statusAndError(HttpStatus.UNPROCESSABLE_ENTITY.value(), error);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(data);
    }
}
