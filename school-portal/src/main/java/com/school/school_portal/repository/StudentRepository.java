package com.school.school_portal.repository;

import com.school.school_portal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    int countByClassField_Id(Integer classId);

    List<Student> findAllByClassField_Id(Integer classId);
}