package com.school.school_portal.service;

import com.school.school_portal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentService studentService;
    private final UserService userService;

    @Autowired
    public StudentService(StudentRepository studentRepository, StudentService studentService, UserService userService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.userService = userService;
    }
}
