package com.school.school_portal.service;

import com.school.school_portal.dto.GradeForm;
import com.school.school_portal.entity.Grade;
import com.school.school_portal.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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

    public List<Grade> getGradesByStudentIdAndClassCourseId(Integer studentId, Integer classCourseId) {
        return gradeRepository.findAllByStudent_IdAndClassCourse_Id(studentId, classCourseId);
    }

    public BigDecimal getAverageGradeByStudentIdAndClassCourseId(Integer studentId, Integer classCourseId) {

        List<Grade> grades = getGradesByStudentIdAndClassCourseId(studentId, classCourseId);
        BigDecimal avg = new BigDecimal(0);

        if (grades.isEmpty()) {
            return avg;
        }

        for (Grade grade : grades) {
            avg = avg.add(grade.getGrade());
        }

        return avg.divide(new BigDecimal(grades.size()), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalAverageGradeByStudentId(Integer studentId) {

        Integer classId = studentService.getStudentById(studentId).getClassField().getId();
        List<Integer> courseIds = classCourseService.getClassCourseIdsByClassId(classId);
        BigDecimal avg = new BigDecimal(0);

        for (Integer courseId : courseIds) {
            avg = avg.add(getAverageGradeByStudentIdAndClassCourseId(studentId, courseId));
        }

        return avg.divide(new BigDecimal(courseIds.size()), 2, RoundingMode.HALF_UP);
    }
}
