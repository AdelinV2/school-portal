package com.school.school_portal.repository;

import com.school.school_portal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    int countByClassField_Id(Integer classId);
}