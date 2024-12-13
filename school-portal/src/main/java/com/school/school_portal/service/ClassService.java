package com.school.school_portal.service;

import com.school.school_portal.dto.ClassForm;
import com.school.school_portal.entity.Teacher;
import com.school.school_portal.entity.Class;
import com.school.school_portal.repository.ClassRepository;
import com.school.school_portal.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {

    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;

    @Autowired
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
        List<Teacher> assignedTutors = classRepository.findAll().stream().map(Class::getTutor).toList();

        return allTeachers.stream().filter(teacher -> !assignedTutors.contains(teacher)).map(teacher ->
                teacher.getUser().getFullName()).collect(Collectors.toList());
    }

    public void saveClass(ClassForm classForm) {

        Class newClass = new Class();

        newClass.setSubject(classForm.getSubject());
        newClass.setYear(classForm.getYear());
        newClass.setSection(classForm.getSection());

        String[] nameParts = classForm.getTutor().split(" ");
        String lastName = nameParts[nameParts.length - 1];
        String firstName = String.join(" ", Arrays.copyOf(nameParts, nameParts.length - 1));

        teacherRepository.findByUser_FirstNameAndUser_LastName(firstName, lastName).ifPresent(newClass::setTutor);

        classRepository.save(newClass);
    }
}
