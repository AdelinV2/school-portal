package com.school.school_portal.service;

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
        return classCourseRepository.findByClassFieldId(classId);
    }

    public ClassCourse getClassCourseByCourseId(Integer courseId) {
        return classCourseRepository.findByCourseId(courseId);
    }

    public List<Integer> getClassCourseIdsByClassId(Integer classId) {
        return classCourseRepository.findCourseIdsByClassField_Id(classId);
    }
}
