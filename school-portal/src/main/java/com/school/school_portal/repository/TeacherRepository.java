package com.school.school_portal.repository;

import com.school.school_portal.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    
    Optional<Teacher> findByUser_FirstNameAndUser_LastName(String firstName, String lastName);
}