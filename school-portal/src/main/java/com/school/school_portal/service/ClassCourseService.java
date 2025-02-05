package com.school.school_portal.service;

import com.school.school_portal.entity.Class;
import com.school.school_portal.entity.ClassCourse;
import com.school.school_portal.repository.ClassCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassCourseService {

    private final ClassCourseRepository classCourseRepository;

    @Autowired
    public ClassCourseService(ClassCourseRepository classCourseRepository) {
        this.classCourseRepository = classCourseRepository;
    }

    public List<ClassCourse> getClassCoursesByClassId(Integer classId) {
        return classCourseRepository.findByClassField_Id(classId);
    }

    public ClassCourse getClassCourseByCourseId(Integer courseId) {
        return classCourseRepository.findByCourse_Id(courseId);
    }

    public List<Integer> getClassCourseIdsByClassId(Integer classId) {
        return classCourseRepository.findCourseIdsByClassField_Id(classId);
    }

    public Class getClassByCourseId(Integer courseId) {
        return classCourseRepository.findByCourse_Id(courseId).getClassField();
    }

    public ClassCourse getClassCourseById(Integer classCourseId) {
        return classCourseRepository.findById(classCourseId).orElse(null);
    }

    public ClassCourse getClassCourseByClassIdAndTeacherId(Integer classId, Integer teacherId) {
        return classCourseRepository.findByClassField_IdAndCourse_Teacher_Id(classId, teacherId);
    }
}
