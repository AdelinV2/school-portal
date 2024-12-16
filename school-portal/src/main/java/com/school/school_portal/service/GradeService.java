package com.school.school_portal.service;

import com.school.school_portal.dto.GradeForm;
import com.school.school_portal.entity.Course;
import com.school.school_portal.entity.Grade;
import com.school.school_portal.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    private final StudentService studentService;
    private final GradeRepository gradeRepository;
    private final ClassCourseService classCourseService;

    @Autowired
    public GradeService(StudentService studentService, GradeRepository gradeRepository, ClassCourseService classCourseService) {
        this.studentService = studentService;
        this.gradeRepository = gradeRepository;
        this.classCourseService = classCourseService;
    }

    public void saveGrade(GradeForm gradeForm) {

        Grade newGrade = new Grade();

        newGrade.setStudent(studentService.getStudentById(gradeForm.getStudentId()));
        newGrade.setClassCourse(classCourseService.getClassCourseByCourseId(gradeForm.getCourseId()));
        newGrade.setGrade(gradeForm.getGrade());
        newGrade.setDateAssigned(gradeForm.getDateAssigned());

        gradeRepository.save(newGrade);
    }
}
