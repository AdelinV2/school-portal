package com.school.school_portal.repository;

import com.school.school_portal.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbsenceRepository extends JpaRepository<Absence, Integer> {

    List<Absence> findByStudent_IdAndExcused(Integer studentId, boolean excused);

    List<Absence> findByStudent_IdAndClassCourse_IdAndExcused(Integer studentId, Integer classCourseId, boolean excused);

    List<Absence> findByStudent_IdAndClassCourse_Id(Integer studentId, Integer classCourseId);
}