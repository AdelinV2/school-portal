package com.school.school_portal.repository;

import com.school.school_portal.entity.ClassCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassCourseRepository extends JpaRepository<ClassCourse, Integer> {

    List<ClassCourse> findByClassField_Id(Integer classId);

    ClassCourse findByCourse_Id(Integer courseId);

    @Query("SELECT c.course.id FROM ClassCourse c WHERE c.classField.id = :classFieldId")
    List<Integer> findCourseIdsByClassField_Id(@Param("classFieldId") Integer classFieldId);

    List<ClassCourse> findAllByClassField_Id(Integer classId);

    ClassCourse findByClassField_IdAndCourse_Teacher_Id(@Param("classId") Integer classId, @Param("teacherId") Integer teacherId);
}