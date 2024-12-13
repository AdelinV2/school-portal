package com.school.school_portal.service;

import com.school.school_portal.entity.Teacher;
import com.school.school_portal.entity.Class;
import com.school.school_portal.repository.ClassRepository;
import com.school.school_portal.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {

    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;

    public ClassService(TeacherRepository teacherRepository, ClassRepository classRepository) {
        this.teacherRepository = teacherRepository;
        this.classRepository = classRepository;
    }

    public List<String> getAllClasses() {
        return Arrays.asList("Natural Science", "Social Science", "Computer Science", "Economics");
    }

    public List<String> getAllYears() {
        return Arrays.asList("V", "VI", "VII", "VIII", "IX", "X", "XI", "XII");
    }

    public List<String> getAllSections() {
        return Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H");
    }

    public List<String> getAllTutors() {

        List<Teacher> allTeachers = teacherRepository.findAll();
        List<Teacher> assignedTutors = classRepository.findAll().stream().map(Class::getTutor).
                collect(Collectors.toList());

        return allTeachers.stream().filter(teacher -> !assignedTutors.contains(teacher)).map(teacher ->
                teacher.getUser().getFullName()).collect(Collectors.toList());
    }
}
