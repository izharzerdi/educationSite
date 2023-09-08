package com.its.education.exception;

public class CourseAlreadyExistsException extends RuntimeException{
    public CourseAlreadyExistsException(String message){
        super(message);
    }
}
