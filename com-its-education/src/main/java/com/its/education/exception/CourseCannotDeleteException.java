package com.its.education.exception;

public class CourseCannotDeleteException extends RuntimeException{
    public CourseCannotDeleteException(String message) {
        super(message);
    }
}
