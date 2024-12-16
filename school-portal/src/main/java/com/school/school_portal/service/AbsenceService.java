package com.school.school_portal.service;

import com.school.school_portal.dto.AbsenceForm;
import com.school.school_portal.entity.Absence;
import com.school.school_portal.repository.AbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final StudentService studentService;
    private final ClassCourseService classCourseService;

    @Autowired
    public AbsenceService(AbsenceRepository absenceRepository, StudentService studentService, ClassCourseService classCourseService) {
        this.absenceRepository = absenceRepository;
        this.studentService = studentService;
        this.classCourseService = classCourseService;
    }

    public void saveAbsence(AbsenceForm absenceForm) {

        Absence absence = new Absence();

        absence.setStudent(studentService.getStudentById(absenceForm.getStudentId()));
        absence.setClassCourse(classCourseService.getClassCourseByCourseId(absenceForm.getClassCourseId()));
        absence.setAbsenceDate(absenceForm.getAbsenceDate());
        absence.setExcused(false);

        absenceRepository.save(absence);
    }

    public List<Absence> getTotalUnexcusedAbsencesByStudentId(Integer studentId) {
        return absenceRepository.findByStudent_IdAndExcused(studentId, false);
    }

    public List<Absence> getTotalExcusedAbsencesByStudentId(Integer studentId) {
        return absenceRepository.findByStudent_IdAndExcused(studentId, true);
    }
}
