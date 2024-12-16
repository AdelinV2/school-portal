package com.school.school_portal.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

public class AbsenceForm {

    @NotEmpty
    private Integer studentId;

    @NotEmpty
    private Integer classCourseId;

    @NotEmpty
    private LocalDate absenceDate;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getClassCourseId() {
        return classCourseId;
    }

    public void setClassCourseId(Integer classCourseId) {
        this.classCourseId = classCourseId;
    }

    public LocalDate getAbsenceDate() {
        return absenceDate;
    }

    public void setAbsenceDate(LocalDate absenceDate) {
        this.absenceDate = absenceDate;
    }
}
