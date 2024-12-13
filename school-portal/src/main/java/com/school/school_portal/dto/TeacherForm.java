package com.school.school_portal.dto;

import jakarta.validation.constraints.NotNull;

public class TeacherForm {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String teachingSubject;

    @NotNull
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTeachingSubject() {
        return teachingSubject;
    }

    public void setTeachingSubject(String teachingSubject) {
        this.teachingSubject = teachingSubject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
