package com.school.school_portal.repository;

import com.school.school_portal.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassRepository extends JpaRepository<Class, Integer> {

    @Query("SELECT DISTINCT c.classField FROM ClassCourse c WHERE c.course.teacher.user.email = :email")
    List<Class> findByTeacherUserEmail(@Param("email") String email);
}