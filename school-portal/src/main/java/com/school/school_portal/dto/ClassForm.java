package com.school.school_portal.dto;

import jakarta.validation.constraints.NotNull;

public class ClassForm {

    @NotNull
    private String subject;

    @NotNull
    private String year;

    @NotNull
    private String section;

    @NotNull
    private String tutor;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }
}
