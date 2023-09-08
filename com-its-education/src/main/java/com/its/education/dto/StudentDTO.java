package com.its.education.dto;

import com.its.education.entity.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {
    private Long id;
    private String fullName;
    private String emailAddress;
    private String telephoneNumber;
    private String address;

    public StudentDTO(Long id) {
        this.id = id;
    }

    public static List<StudentDTO> toDTO(List<Student> students){
        List<StudentDTO> studentDTOS = new ArrayList<>();
        students.forEach(student -> {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setId(student.getId());
            studentDTO.setFullName(student.getFullName());
            studentDTO.setEmailAddress(student.getEmailAddress());
            studentDTO.setTelephoneNumber(student.getTelephoneNumber());
            studentDTOS.add(studentDTO);
        });
        return studentDTOS;
    }

    public static Student toEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setFullName(studentDTO.getFullName());
        student.setEmailAddress(studentDTO.getEmailAddress());
        student.setTelephoneNumber(studentDTO.getTelephoneNumber());
        student.setAddress(studentDTO.getAddress());
        return student;
    }
}
