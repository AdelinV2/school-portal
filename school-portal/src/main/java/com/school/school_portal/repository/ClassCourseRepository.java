package com.school.school_portal.repository;

import com.school.school_portal.entity.ClassCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassCourseRepository extends JpaRepository<ClassCourse, Integer> {

    List<ClassCourse> findByClassId(Integer classId);
}