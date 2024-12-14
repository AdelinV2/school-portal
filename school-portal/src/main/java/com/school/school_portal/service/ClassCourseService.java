package com.school.school_portal.service;

import com.school.school_portal.repository.ClassCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassCourseService {

    private final ClassCourseRepository classCourseRepository;

    @Autowired
    public ClassCourseService(ClassCourseRepository classCourseRepository) {
        this.classCourseRepository = classCourseRepository;
    }


}
