package com.school.school_portal.dto;

import jakarta.validation.constraints.NotNull;

public class CourseForm {

    @NotNull
    private String subject;

    @NotNull
    private String teacher;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
