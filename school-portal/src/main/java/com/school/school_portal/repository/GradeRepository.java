package com.school.school_portal.repository;

import com.school.school_portal.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findAllByStudent_IdAndClassCourse_Id(Integer studentId, Integer classCourseId);
}