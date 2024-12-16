package com.school.school_portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    private final StudentService studentService;

    @Autowired
    public GradeService(StudentService studentService) {
        this.studentService = studentService;
    }
}
