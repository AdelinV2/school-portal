package com.school.school_portal.repository;

import com.school.school_portal.entity.ClassCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassCourseRepository extends JpaRepository<ClassCourse, Integer> {
}