package com.school.school_portal.dto;

import jakarta.validation.constraints.NotEmpty;

public class AbsenceForm {

    @NotEmpty
    private String studentId;

    @NotEmpty
    private String classCourseId;

    @NotEmpty
    private String absenceDate;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassCourseId() {
        return classCourseId;
    }

    public void setClassCourseId(String classCourseId) {
        this.classCourseId = classCourseId;
    }

    public String getAbsenceDate() {
        return absenceDate;
    }

    public void setAbsenceDate(String absenceDate) {
        this.absenceDate = absenceDate;
    }
}
