package com.school.school_portal.service;

import com.school.school_portal.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final StudentService studentService;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository, StudentService studentService) {
        this.absenceRepository = absenceRepository;
        this.studentService = studentService;
    }
}
