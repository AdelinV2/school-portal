package com.school.school_portal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "absences")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "class_course_id", nullable = false)
    private ClassCourse classCourse;

    @NotNull
    @Column(name = "absence_date", nullable = false)
    private LocalDate absenceDate;

    @ColumnDefault("0")
    @Column(name = "excused")
    private Boolean excused;

    public Absence() {
    }

    public Absence(Integer id, Student student, ClassCourse classCourse, LocalDate absenceDate, Boolean excused) {
        this.id = id;
        this.student = student;
        this.classCourse = classCourse;
        this.absenceDate = absenceDate;
        this.excused = excused;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassCourse getClassCourse() {
        return classCourse;
    }

    public void setClassCourse(ClassCourse classCourse) {
        this.classCourse = classCourse;
    }

    public LocalDate getAbsenceDate() {
        return absenceDate;
    }

    public void setAbsenceDate(LocalDate absenceDate) {
        this.absenceDate = absenceDate;
    }

    public Boolean getExcused() {
        return excused;
    }

    public void setExcused(Boolean excused) {
        this.excused = excused;
    }

}