package com.school.school_portal.service;

import com.school.school_portal.entity.Student;
import com.school.school_portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserService userService;

    @Autowired
    public StudentService(StudentRepository studentRepository, UserService userService) {
        this.studentRepository = studentRepository;
        this.userService = userService;
    }

    public List<Student> getStudentsByClassId(Integer classId) {
        return studentRepository.findAllByClassField_Id(classId);
    }
}
